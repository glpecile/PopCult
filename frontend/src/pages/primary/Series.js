import MediaSlider from "../../components/media/MediaSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useContext, useEffect, useState} from "react";
import MediaService from "../../services/MediaService";
import Loader from "../secondary/errors/Loader";
import useErrorStatus from "../../hooks/useErrorStatus";
import {createSearchParams, useNavigate} from "react-router-dom";
import GenresContext from "../../store/GenresContext";
import Filters from "../../components/search/filters/Filters";
import MediaCard from "../../components/media/MediaCard";
import PaginationComponent from "../../components/PaginationComponent";


function Series() {
    const [t] = useTranslation();
    const navigate = useNavigate();

    const [carrouselData, setCarrouselData] = useState(undefined);
    const [series, setSeries] = useState(undefined);
    const [page, setPage] = useState(1);
    const {setErrorStatusCode} = useErrorStatus();
    const [seriesFilters, setSeriesFilters] = useState(() => new Map());
    const genres = useContext(GenresContext).genres;
    const pageSize = 4;

    const mediaSort = 'sort';
    const mediaDecades = 'decades';
    const mediaCategories = 'categories';

    useEffect(() => {
        const getCarrouselData = async () => {
            try {
                let data = await MediaService.getSeries({pageSize: 12});
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
        navigate({
            pathname: '/media/series',
            search: createSearchParams({
                page: page,
                ...Object.fromEntries(seriesFilters.entries())
            }).toString()
        });
    }, [page, navigate, seriesFilters]);

    return (
        <section>
            <Helmet>
                <title>{t('series_title')}</title>
            </Helmet>
            {
                !carrouselData ? <Loader/> :
                    <>
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
                            <div className="row py-2">
                                {series.data.map((content) => {
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
                                {(series.data.length > 0 && series.links.last.page > 1) &&
                                    <PaginationComponent page={page} lastPage={series.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </> : <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
                    </>
            }
        </section>
    );
}

export default Series;