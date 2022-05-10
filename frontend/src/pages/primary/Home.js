import Loader from "../secondary/errors/Loader";
import {useContext, useEffect, useState} from "react";
import AuthContext from "../../store/AuthContext";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import useErrorStatus from "../../hooks/useErrorStatus";
import listService from "../../services/ListService";
import userService from "../../services/UserService";
import favoriteService from "../../services/FavoriteService";
import ListsSlider from "../../components/lists/ListsSlider";
import mediaService from "../../services/MediaService";
import MediaSlider from "../../components/media/MediaSlider";

export default function Home() {
    const authContext = useContext(AuthContext);
    const {t} = useTranslation();

    const navigate = useNavigate();
    const pageSize = 12;

    const [user, setUser] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [films, setFilms] = useState(undefined);
    const [series, setSeries] = useState(undefined);

    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getUser() {
            if (authContext.isLoggedIn) {
                try {
                    const data = await userService.getUserByUsername(authContext.username);
                    setUser(data);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }
        }

        getUser();
    }, [setErrorStatusCode, authContext]);

    useEffect(() => {
        async function getInitialData() {
            try {
                if (authContext.isLoggedIn) {
                    if (user) {
                        const l = await favoriteService.getUserRecommendedLists({
                            url: user.recommendedListsUrl,
                            pageSize: pageSize
                        });
                        setLists(l.data);
                        const f = await favoriteService.getUserRecommendedFilms({
                            url: user.recommendedMediaUrl,
                            pageSize: pageSize,
                        });
                        setFilms(f.data);
                        const s = await favoriteService.getUserRecommendedSeries({
                            url: user.recommendedMediaUrl,
                            pageSize: pageSize,
                        });
                        setSeries(s.data);
                    }
                } else {
                    const l = await listService.getLists({pageSize: pageSize, sortType: "DATE"});
                    setLists(l.data);
                    const f = await mediaService.getFilms({pageSize: pageSize, sortType: "DATE"});
                    setFilms(f.data);
                    const s = await mediaService.getSeries({pageSize: pageSize, sortType: "DATE"});
                    setSeries(s.data);
                }
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getInitialData();
    }, [setErrorStatusCode, authContext, pageSize, user]);

    return (<div>
        {(lists || series || films) ?
            (<>
                <div className="flex flex-col justify-center items-center py-4 mx-auto">
                    {!authContext.isLoggedIn ? <>
                        <h1 className="text-center text-3xl">
                            {t('home_slogan')}
                        </h1>
                        <button
                            className="btn btn-link text-center text-white bg-violet-500 hover:bg-violet-900 rounded-full shadow-md hover:shadow-lg my-4 w-1/4"
                            onClick={() => {
                                navigate('/register')
                            }}>{t('home_register')}</button>
                    </> : <>
                        <h1 className="text-center text-3xl">
                            {t('home_greeting')}
                        </h1>
                        <button className="text-center text-5xl font-bold text-violet-500 hover:text-violet-900"
                                onClick={() => {
                                    navigate({pathname: 'user/' + authContext.username})
                                }}>{authContext.username}</button>
                    </>}
                </div>
                {/*Lists*/}
                {lists &&
                    <>
                        <div className="flex justify-between">
                            <h2 className="font-bold text-2xl pt-2">
                                {!authContext.isLoggedIn ? <>{t('home_title_lists')}</> : <>{t('home_title_lists_discovery')}</>}
                            </h2>
                            <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded"
                                    onClick={() => navigate('/lists')}>
                                {t('home_viewAll')}
                            </button>
                        </div>
                        <ListsSlider lists={lists}/>
                    </>}
                {/*Films*/}
                {films &&
                    <>
                        <div className="flex justify-between">
                            <h2 className="font-bold text-2xl pt-2">
                                {!authContext.isLoggedIn ? <>{t('home_title_films')}</> : <>{t('home_title_films_discovery')}</>}
                            </h2>
                            <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded"
                                    onClick={() => navigate('/media/films')}>
                                {t('home_viewAll')}
                            </button>
                        </div>
                        <MediaSlider media={films}/>
                    </>}
                {/*Series*/}
                {series &&
                    <>
                        <div className="flex justify-between">
                            <h2 className="font-bold text-2xl pt-2">
                                {!authContext.isLoggedIn ? <>{t('home_title_series')}</> : <>{t('home_title_series_discovery')}</>}
                            </h2>
                            <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded"
                                    onClick={() => navigate('/media/series')}>
                                {t('home_viewAll')}
                            </button>
                        </div>
                        <MediaSlider media={series}/>
                    </>}
            </>)
            :
            <Loader/>
        }
    </div>);
}