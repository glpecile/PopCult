import ListsSlider from "../../components/lists/ListsSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {createSearchParams, useLocation, useNavigate, useSearchParams} from "react-router-dom";
import {useCallback, useContext, useEffect, useState} from "react";
import useErrorStatus from "../../hooks/useErrorStatus";
import listService from "../../services/ListService";
import Loader from "../secondary/errors/Loader";
import Filters from "../../components/search/filters/Filters";
import ListsCard from "../../components/lists/ListsCard";
import PaginationComponent from "../../components/PaginationComponent";
import GenresContext from "../../store/GenresContext";
import {Alert, Snackbar} from "@mui/material";
import ResponsiveMediaGrid from "../../components/ResponsiveMediaGrid";

function Lists() {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const genres = useContext(GenresContext).genres;
    const [searchParams] = useSearchParams();


    const [carrouselLists, setCarrouselLists] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [page, setPage] = useState(searchParams.get("page") || 1);
    const {setErrorStatusCode} = useErrorStatus();
    const [listFilters, setListFilters] = useState(() => new Map());
    const pageSize = 12;

    const listSort = 'sort';
    const listDecades = 'decades';
    const listCategories = 'categories';
    const location = useLocation()
    const [showAlert, setShowAlert] = useState(location.state && location.state.data === 204);

    useEffect(() => {
        if (searchParams.has(listCategories)) setListFilters(prev => new Map([...prev, [listCategories, searchParams.getAll(listCategories)]]));
        if (searchParams.has(listSort)) setListFilters(prev => new Map([...prev, [listSort, searchParams.get(listSort)]]));
        if (searchParams.has(listDecades)) setListFilters(prev => new Map([...prev, [listDecades, searchParams.get(listDecades)]]));
    }, [searchParams]);

    const getCarrouselLists = useCallback(async () => {
        const data = await listService.getLists({pageSize: 12})
        setCarrouselLists(data.data);
    }, []);

    useEffect(() => {
        try {
            getCarrouselLists();
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }, [setErrorStatusCode, getCarrouselLists]);

    useEffect(() => {
        async function getLists() {
            try {
                const data = await listService.getLists({
                    page: page, pageSize: pageSize, genres: listFilters.get(listCategories),
                    sortType: listFilters.get(listSort),
                    decades: listFilters.get(listDecades)
                })
                setLists(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getLists();
    }, [page, setErrorStatusCode, listFilters, pageSize]);

    const createNewList = () => {
        navigate('/lists/new');
    }

    const applyFilters = () => {
        navigate({
            pathname: '/lists',
            search: createSearchParams({
                page: page,
                ...Object.fromEntries(listFilters.entries())
            }).toString()
        });
    };

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setShowAlert(false);
            }, 3000);
            return () => clearTimeout(timeOut);
        }
        , [showAlert]);

    return (
        <section>
            <Helmet>
                <title>{t('lists_title')}</title>
            </Helmet>
            {carrouselLists ? <>
                <div className="flex flex-wrap justify-between p-2.5 pb-0">
                    <h4 className="font-bold text-2xl pt-2">
                        {t('lists_popular')}
                    </h4>
                    <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                            onClick={createNewList}>
                        {t('lists_newList')}
                    </button>
                </div>
                <ListsSlider lists={carrouselLists}/>
                <h4 className="font-bold text-2xl pt-2">
                    {t('lists_explore')}
                </h4>
                <Filters showMediaFilters={false}
                         setListPage={setPage}
                         setListFilters={setListFilters} listFilters={listFilters} genres={genres} listSort={listSort}
                         listDecades={listDecades} listCategories={listCategories} applyFilters={applyFilters}/>
                {(lists && lists.data) ? <>
                    <ResponsiveMediaGrid>
                        {lists.data.map((content) => {
                            return <div className="m-0 p-0" key={content.id}>
                                <ListsCard
                                    id={content.id}
                                    key={content.id}
                                    mediaUrl={content.mediaUrl}
                                    listTitle={content.name}/>
                            </div>
                        })}
                    </ResponsiveMediaGrid>
                    <div className="flex justify-center mt-4">
                        {(lists.data.length > 0 && lists.links.last.page > 1) &&
                            <PaginationComponent page={page} lastPage={lists.links.last.page}
                                                 setPage={setPage}/>
                        }
                    </div>
                </> : <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
                <Snackbar open={showAlert} autoHideDuration={6000}
                          anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                    <Alert severity="success">
                        {t('report_admin_success')}
                    </Alert>
                </Snackbar>
            </> : <Loader/>}
        </section>
    );
}

export default Lists;