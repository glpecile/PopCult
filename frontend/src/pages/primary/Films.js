import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
import MediaService from "../../services/MediaService";
import Loader from "../secondary/errors/Loader";
import MediaSlider from "../../components/media/MediaSlider";
import useErrorStatus from "../../hooks/useErrorStatus";
import Filters from "../../components/search/filters/Filters";
import PaginationComponent from "../../components/PaginationComponent";
import GenresContext from "../../store/GenresContext";
import MediaCard from "../../components/media/MediaCard";
import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import ResponsiveMediaGrid from "../../components/ResponsiveMediaGrid";

export default function Films() {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();


    const [carrouselData, setCarrouselData] = useState(undefined);
    const [page, setPage] = useState(searchParams.get("page") || 1);
    const [films, setFilms] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();
    const mediaCategories = 'categories';
    const mediaDecades = 'decades';
    const mediaSort = 'sort';
    const getMediaFilters = useCallback(() => {
        const aux = new Map();
        if (searchParams.has(mediaDecades)) aux.set(mediaDecades, searchParams.get(mediaDecades));
        if (searchParams.has(mediaCategories)) aux.set(mediaCategories, searchParams.getAll(mediaCategories));
        if (searchParams.has(mediaSort)) aux.set(mediaSort, searchParams.get(mediaSort));
        return aux;
    }, [searchParams]);

    const [filmFilters, setFilmFilters] = useState(getMediaFilters);
    const genres = useContext(GenresContext).genres;
    let firstLoad = useRef(true);
    const pageSize = 12;

    useEffect(() => {
        setPage(searchParams.get("page"));
        setFilmFilters(getMediaFilters)
    }, [searchParams, getMediaFilters]);

    const getCarrouselData = useCallback(async () => {
        let data = await MediaService.getFilms({pageSize: 12});
        setCarrouselData(data);
    }, []);

    useEffect(() => {
        try {
            getCarrouselData();
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }, [setErrorStatusCode, getCarrouselData]);

    useEffect(() => {
        async function getData() {
            try {
                const data = await MediaService.getFilms({
                    page: page,
                    pageSize: pageSize,
                    genres: filmFilters.get(mediaCategories),
                    sortType: filmFilters.get(mediaSort),
                    decades: filmFilters.get(mediaDecades)
                });
                setFilms(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);

            }
        }

        getData();
    }, [searchParams, setErrorStatusCode])

    useEffect(() => {
        if (firstLoad.current !== true)
            navigate({
                pathname: '/media/films',
                search: createSearchParams({
                    page: page,
                    ...Object.fromEntries(filmFilters.entries())
                }).toString()
            });
        firstLoad.current = false;
        return () => {
            firstLoad.current = false;
        }
    }, [page, filmFilters, navigate]);


    return (
        <section>
            <Helmet>
                <title>{t('films_title')}</title>
            </Helmet>
            {
                !carrouselData ? <Loader/> :
                    <>
                        <h4 className="font-bold text-2xl pt-2">
                            {t('films_popular')}
                        </h4>
                        <MediaSlider media={carrouselData.data}/>
                        <h4 className="font-bold text-2xl pt-2">
                            {t('films_explore')}
                        </h4>
                        <Filters showMediaFilters={true} showMediaType={false} setMediaFilters={setFilmFilters}
                                 mediaFilters={filmFilters}
                                 setMediaPage={setPage} genres={genres} mediaSort={mediaSort}
                                 mediaDecades={mediaDecades} mediaCategories={mediaCategories}/>
                        {(films && films.data) ? <>
                            <ResponsiveMediaGrid>
                                {films.data.map((content) => {
                                    return <div className="p-0 m-0" key={content.id}>
                                        <MediaCard
                                            key={content.id}
                                            id={content.id}
                                            image={content.imageUrl}
                                            title={content.title}
                                            releaseDate={content.releaseDate.slice(0, 4)}
                                            type={content.type.toLowerCase()}
                                        /></div>
                                })}
                            </ResponsiveMediaGrid>
                            <div className="flex justify-center pt-4">
                                {(films.data.length > 0 && films.links.last.page > 1) &&
                                    <PaginationComponent page={page} lastPage={films.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </> : <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
                    </>
            }
        </section>
    );
}