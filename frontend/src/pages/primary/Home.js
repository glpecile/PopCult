import Loader from "../secondary/errors/Loader";
import {useCallback, useContext, useEffect, useState} from "react";
import AuthContext from "../../store/AuthContext";
import {createSearchParams, Link, useNavigate} from "react-router-dom";
import {Trans, useTranslation} from "react-i18next";
import useErrorStatus from "../../hooks/useErrorStatus";
import listService from "../../services/ListService";
import favoriteService from "../../services/FavoriteService";
import ListsSlider from "../../components/lists/ListsSlider";
import mediaService from "../../services/MediaService";
import MediaSlider from "../../components/media/MediaSlider";
import Spinner from "../../components/animation/Spinner";
import UserContext from "../../store/UserContext";

export default function Home() {
    const authContext = useContext(AuthContext);
    const user = useContext(UserContext).user;

    const {t} = useTranslation();
    const navigate = useNavigate();

    const pageSize = 12;
    const [lists, setLists] = useState(undefined);
    const [films, setFilms] = useState(undefined);
    const [series, setSeries] = useState(undefined);

    const {setErrorStatusCode} = useErrorStatus();

    /** lists **/
    const getUserLists = useCallback(async () => {
        if (user) {
            const l = await favoriteService.getUserRecommendedLists({
                url: user.recommendedListsUrl,
                pageSize: pageSize
            });
            setLists(l.data);
        }
    }, [user, pageSize]);

    const getLists = useCallback(async () => {
        const l = await listService.getLists({pageSize: pageSize, sortType: "DATE"});
        setLists(l.data);
    }, [pageSize]);

    useEffect(() => {
        try {
            if (authContext.isLoggedIn) {
                getUserLists();
            } else {
                getLists();
            }
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }, [authContext.isLoggedIn, setErrorStatusCode, user, getUserLists, getLists]);

    /** films **/
    const getUserFilms = useCallback(async () => {
        if (user) {
            const f = await favoriteService.getUserRecommendedFilms({
                url: user.recommendedMediaUrl,
                pageSize: pageSize,
            });
            setFilms(f.data);
        }
    }, [user, pageSize]);

    const getFilms = useCallback(async () => {
        const f = await mediaService.getFilms({pageSize: pageSize, sortType: "DATE"});
        setFilms(f.data);
    }, [pageSize]);

    useEffect(() => {
        try {
            if (authContext.isLoggedIn) {
                getUserFilms();
            } else {
                getFilms();
            }
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }, [authContext.isLoggedIn, setErrorStatusCode, user, getUserFilms, getFilms]);

    /** series **/
    const getUserSeries = useCallback(async () => {
        if (user) {
            const s = await favoriteService.getUserRecommendedSeries({
                url: user.recommendedMediaUrl,
                pageSize: pageSize,
            });
            setSeries(s.data);
        }
    }, [user, pageSize]);

    const getSeries = useCallback(async () => {
        const s = await mediaService.getSeries({pageSize: pageSize, sortType: "DATE"});
        setSeries(s.data);
    }, [pageSize]);

    useEffect(() => {
        try {
            if (authContext.isLoggedIn) {
                getUserSeries();
            } else {
                getSeries();
            }
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }, [authContext.isLoggedIn, setErrorStatusCode, user, getUserSeries, getSeries]);

    return (<div>
        {(lists || series || films) ?
            (<>
                <div className="flex flex-col justify-center items-center py-4 mx-auto">
                    {!authContext.isLoggedIn ? <>
                        <h1 className="text-center text-3xl">
                            {t('home_slogan')}
                        </h1>
                        <Link className="btn btn-secondary text-center text-white bg-violet-500 hover:shadow-violet-500/50 rounded-full shadow-md my-4 w-1/4" to={'/register'}>
                            {t('home_register')}
                        </Link>
                    </> : <>
                        <h1 className="text-center text-3xl flex flex-col">
                            <Trans i18nKey="home_greeting">
                                <button className="text-center text-5xl font-bold text-violet-500 hover:text-violet-900"
                                        onClick={() => {
                                            navigate({pathname: 'user/' + authContext.username})
                                        }}>
                                    {{username: authContext.username}}
                                </button>
                            </Trans>
                        </h1>
                    </>}
                </div>
                {/* Lists */}
                {lists ?
                    <>
                        <div className="flex justify-between">
                            <h2 className="font-bold text-2xl pt-2">
                                {!authContext.isLoggedIn ? <>{t('home_title_lists')}</> : <>{t('home_title_lists_discovery')}</>}
                            </h2>
                            <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded"
                                    onClick={() => navigate({
                                        pathname: '/lists', search: createSearchParams({
                                            page: 1
                                        }).toString()
                                    })}>
                                {t('home_viewAll')}
                            </button>
                        </div>
                        <ListsSlider lists={lists}/>
                    </> : <Spinner/>}
                {/* Films */}
                {films ?
                    <>
                        <div className="flex justify-between">
                            <h2 className="font-bold text-2xl pt-2">
                                {!authContext.isLoggedIn ? <>{t('home_title_films')}</> : <>{t('home_title_films_discovery')}</>}
                            </h2>
                            <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded"
                                    onClick={() => navigate({
                                        pathname: '/media/films', search: createSearchParams({
                                            page: 1
                                        }).toString()
                                    })}>
                                {t('home_viewAll')}
                            </button>
                        </div>
                        <MediaSlider media={films}/>
                    </> : <Spinner/>}
                {/* Series */}
                {series ?
                    <>
                        <div className="flex justify-between">
                            <h2 className="font-bold text-2xl pt-2">
                                {!authContext.isLoggedIn ? <>{t('home_title_series')}</> : <>{t('home_title_series_discovery')}</>}
                            </h2>
                            <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded"
                                    onClick={() => navigate({
                                        pathname: '/media/series', search: createSearchParams({
                                            page: 1
                                        }).toString()
                                    })}>
                                {t('home_viewAll')}
                            </button>
                        </div>
                        <MediaSlider media={series}/>
                    </> : <Spinner/>}
            </>)
            :
            <Loader/>
        }
    </div>);
}