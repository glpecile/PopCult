import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useContext, useEffect, useState} from "react";
import MediaService from "../../services/MediaService";
import Loader from "../secondary/errors/Loader";
import MediaSlider from "../../components/media/MediaSlider";
import useErrorStatus from "../../hooks/useErrorStatus";
import Filters from "../../components/search/filters/Filters";
import PaginationComponent from "../../components/PaginationComponent";
import GenresContext from "../../store/GenresContext";
import MediaCard from "../../components/media/MediaCard";
import {createSearchParams, useNavigate} from "react-router-dom";

export default function Films() {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const [carrouselData, setCarrouselData] = useState(undefined);
    const [films, setFilms] = useState(undefined);
    const [page, setPage] = useState(1);
    const {setErrorStatusCode} = useErrorStatus();
    const [filmFilters, setFilmFilters] = useState(() => new Map());
    const genres = useContext(GenresContext).genres;
    const pageSize = 4;

    const mediaSort = 'sort';
    const mediaDecades = 'decades';
    const mediaCategories = 'categories';

    useEffect(() => {
        const getCarrouselData = async () => {
            try {
                let data = await MediaService.getFilms({pageSize: 12});
                setCarrouselData(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        };
        getCarrouselData();
    }, [setErrorStatusCode]);

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
    }, [page, filmFilters, pageSize, setErrorStatusCode])

    useEffect(() => {
        navigate({
            pathname: '/media/films',
            search: createSearchParams({
                page: page,
                ...Object.fromEntries(filmFilters.entries())
            }).toString()
        });
    }, [page, navigate, filmFilters]);


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
                            <div className="row py-2">
                                {films.data.map((content) => {
                                    return <div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                                                key={content.id}>
                                        <MediaCard
                                            key={content.id}
                                            id={content.id}
                                            image={content.imageUrl}
                                            title={content.title}
                                            releaseDate={content.releaseDate.slice(0, 4)}
                                            type={content.type.toLowerCase()}
                                        /></div>
                                })}
                            </div>
                            <div className="flex justify-center pt-2">
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