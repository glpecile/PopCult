import ListsSlider from "../../components/lists/ListsSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {createSearchParams, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import useErrorStatus from "../../hooks/useErrorStatus";
import listService from "../../services/ListService";
import Loader from "../secondary/errors/Loader";
import Filters from "../../components/search/filters/Filters";
import GenreService from "../../services/GenreService";
import ListsCard from "../../components/lists/ListsCard";
import PaginationComponent from "../../components/PaginationComponent";

function Lists() {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const [genres, setGenres] = useState(undefined);


    const [carrouselLists, setCarrouselLists] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [page, setPage] = useState(1);
    const {setErrorStatusCode} = useErrorStatus();
    const [listFilters, setListFilters] = useState(() => new Map());
    const pageSize = 4;

    const listSort = 'sort';
    const listDecades = 'decades';
    const listCategories = 'categories';

    useEffect(() => {
        async function fillGenres() {
            try {
                const data = await GenreService.getGenres();
                setGenres(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        fillGenres();
    }, [setErrorStatusCode]);

    useEffect(() => {
        async function getCarrouselLists() {
            try {
                const data = await listService.getLists({page: 1, pageSize: 12})
                setCarrouselLists(data.data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getCarrouselLists();
    }, []);

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

    useEffect(() => {
        navigate({
            pathname: '/lists',
            search: createSearchParams({
                page: page,
                ...Object.fromEntries(listFilters.entries())
            }).toString()
        });
    }, [page, navigate, listFilters]);

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
                         listDecades={listDecades} listCategories={listCategories}/>
                {(lists && lists.data) ? <>
                    <div className="row py-2">
                        {lists.data.map((content) => {
                            return <div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                                        key={content.id}>
                                <ListsCard id={content.id} key={content.id}
                                           mediaUrl={content.mediaUrl}
                                           listTitle={content.name}/></div>
                        })}
                    </div>
                    <div className="flex justify-center pt-2">
                        {(lists.data.length > 0 && lists.links.last.page > 1) &&
                            <PaginationComponent page={page} lastPage={lists.links.last.page}
                                                 setPage={setPage}/>
                        }
                    </div>
                </> : <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
            </> : <Loader/>}
        </section>
    );
}

export default Lists;