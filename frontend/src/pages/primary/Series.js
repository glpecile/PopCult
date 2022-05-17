import MediaSlider from "../../components/media/MediaSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
import MediaService from "../../services/MediaService";
import Loader from "../secondary/errors/Loader";
import useErrorStatus from "../../hooks/useErrorStatus";
import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import GenresContext from "../../store/GenresContext";
import Filters from "../../components/search/filters/Filters";
import MediaCard from "../../components/media/MediaCard";
import PaginationComponent from "../../components/PaginationComponent";
import ResponsiveMediaGrid from "../../components/ResponsiveMediaGrid";


function Series() {
    const [t] = useTranslation();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    const [carrouselData, setCarrouselData] = useState(undefined);
    const [series, setSeries] = useState(undefined);
    const [page, setPage] = useState(searchParams.get("page") || 1);
    const {setErrorStatusCode} = useErrorStatus();
    const genres = useContext(GenresContext).genres;
    const pageSize = 12;

    const mediaSort = 'sort';
    const mediaDecades = 'decades';
    const mediaCategories = 'categories';
    const getMediaFilters = useCallback(() => {
        const aux = new Map();
        if (searchParams.has(mediaCategories)) aux.set(mediaCategories, searchParams.getAll(mediaCategories));
        if (searchParams.has(mediaDecades)) aux.set(mediaDecades, searchParams.get(mediaDecades));
        if (searchParams.has(mediaSort)) aux.set(mediaSort, searchParams.get(mediaSort));
        return aux;
    }, [searchParams]);

    const [seriesFilters, setSeriesFilters] = useState(getMediaFilters);
    let firstLoad = useRef(true);

    useEffect(() => {
        firstLoad.current = true;
        setPage(searchParams.get("page"));
        setSeriesFilters(getMediaFilters)
    }, [searchParams, getMediaFilters]);

    const getCarrouselData = useCallback(async () => {
        let data = await MediaService.getSeries({pageSize: 12});
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
                const data = await MediaService.getSeries({
                    page: page,
                    pageSize: pageSize,
                    genres: seriesFilters.get(mediaCategories),
                    sortType: seriesFilters.get(mediaSort),
                    decades: seriesFilters.get(mediaDecades)
                });
                setSeries(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);

            }
        }

        getData();
    }, [page, seriesFilters, pageSize, setErrorStatusCode])

    useEffect(() => {
        if (firstLoad.current !== true)
            navigate({
                pathname: '/media/series', search: createSearchParams({
                    page: page,
                    ...Object.fromEntries(seriesFilters.entries())
                }).toString()
            });
        firstLoad.current = false;
    }, [page, seriesFilters, navigate]);

    return (<section>
        <Helmet>
            <title>{t('series_title')}</title>
        </Helmet>
        {!carrouselData ? <Loader/> : <>
            <h4 className="font-bold text-2xl pt-2">
                {t('series_popular')}
            </h4>
            <MediaSlider media={carrouselData.data}/>
            <h4 className="font-bold text-2xl pt-2">
                {t('series_explore')}
            </h4>
            <Filters showMediaFilters={true} showMediaType={false} setMediaFilters={setSeriesFilters}
                     mediaFilters={seriesFilters}
                     setMediaPage={setPage} genres={genres} mediaSort={mediaSort}
                     mediaDecades={mediaDecades} mediaCategories={mediaCategories}/>
            {(series && series.data) ? <>
                <ResponsiveMediaGrid>
                    {series.data.map((content) => {
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
                <div className="flex justify-center pt-2">
                    {(series.data.length > 0 && series.links.last.page > 1) &&
                        <PaginationComponent page={page} lastPage={series.links.last.page}
                                             setPage={setPage}/>}
                </div>
            </> : <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
        </>}
    </section>);
}

export default Series;