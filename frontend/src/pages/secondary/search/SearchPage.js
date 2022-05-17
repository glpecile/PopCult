import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import Filters from "../../../components/search/filters/Filters";
import SearchResults from "../../../components/search/SearchResults";
import {useContext, useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import mediaService from "../../../services/MediaService";
import listService from "../../../services/ListService";
import Spinner from "../../../components/animation/Spinner";
import {useTranslation} from "react-i18next";
import GenresContext from "../../../store/GenresContext";

export default function SearchPage() {
    const {t} = useTranslation();
    const [searchParams] = useSearchParams();
    const term = searchParams.get("term");

    const navigate = useNavigate();
    const genres = useContext(GenresContext).genres;
    const [media, setMedia] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [mediaPage, setMediaPage] = useState(searchParams.get("mp") || 1);
    const [listPage, setListPage] = useState(searchParams.get("lp") || 1);
    const [activeTab, setActiveTab] = useState(0); //use this in order to know which options of filters to give
    const [mediaFilters, setMediaFilters] = useState(() => new Map());
    const [listFilters, setListFilters] = useState(() => new Map());
    const pageSize = 4;
    const {setErrorStatusCode} = useErrorStatus();

    const mediaSort = 'msort';
    const mediaType = 'mtype';
    const mediaDecades = 'mdecades';
    const mediaCategories = 'mcategories';
    const listSort = 'lsort';
    const listDecades = 'ldecades';
    const listCategories = 'lcategories';


    useEffect(() => {
        if (searchParams.has(mediaCategories)) setMediaFilters(prev => new Map([...prev, [mediaCategories, searchParams.getAll(mediaCategories)]]));
        if (searchParams.has(mediaType)) setMediaFilters(prev => new Map([...prev, [mediaType, searchParams.get(mediaType)]]));
        if (searchParams.has(mediaDecades)) setMediaFilters(prev => new Map([...prev, [mediaDecades, searchParams.get(mediaDecades)]]));
        if (searchParams.has(mediaSort)) setMediaFilters(prev => new Map([...prev, [mediaSort, searchParams.get(mediaSort)]]));
        if (searchParams.has(listCategories)) setListFilters(prev => new Map([...prev, [listCategories, searchParams.getAll(listCategories)]]));
        if (searchParams.has(listSort)) setListFilters(prev => new Map([...prev, [listSort, searchParams.get(listSort)]]));
        if (searchParams.has(listDecades)) setListFilters(prev => new Map([...prev, [listDecades, searchParams.get(listDecades)]]));
    }, [searchParams]);

    const applyFilters = () => {
        navigate({
            pathname: '/search',
            search: createSearchParams({
                term: term,
                mp: mediaPage,
                lp: listPage,
                ...Object.fromEntries(mediaFilters.entries()),
                ...Object.fromEntries(listFilters.entries())
            }).toString()
        });
    }


    useEffect(() => {
        async function getMedia() {
            try {
                const data = await mediaService.getMediaList({
                    page: mediaPage,
                    pageSize: pageSize,
                    query: term,
                    mediaType: mediaFilters.get(mediaType),
                    genres: mediaFilters.get(mediaCategories),
                    sortType: mediaFilters.get(mediaSort),
                    decades: mediaFilters.get(mediaDecades)
                })
                setMedia(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getMedia();
    }, [term, mediaPage, setErrorStatusCode, mediaFilters]);

    useEffect(() => {
        async function getLists() {
            try {
                const data = await listService.getLists({
                    page: listPage, pageSize: pageSize, query: term, genres: listFilters.get(listCategories),
                    sortType: listFilters.get(listSort),
                    decades: listFilters.get(listDecades)
                })
                setLists(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getLists();
    }, [term, listPage, setErrorStatusCode, listFilters]);

    return (
        <div>
            <h1 className="text-3xl font-black justify-start p-2 break-words max-w-full tracking-wide">
                {term.length ? t('search_title', {term: term}) : t('search_title_all')}
            </h1>
            {<Filters showMediaFilters={activeTab === 0} showMediaType={true} setMediaFilters={setMediaFilters}
                      mediaFilters={mediaFilters}
                      setListPage={setListPage} setMediaPage={setMediaPage}
                      setListFilters={setListFilters} listFilters={listFilters} genres={genres} mediaSort={mediaSort}
                      mediaType={mediaType} mediaDecades={mediaDecades} mediaCategories={mediaCategories}
                      listSort={listSort} listDecades={listDecades} listCategories={listCategories}
                      applyFilters={applyFilters}/>}
            {(media && lists) ?
                <SearchResults media={media} mediaPage={mediaPage} setMediaPage={setMediaPage} lists={lists}
                               listPage={listPage} setListPage={setListPage} activeTab={activeTab}
                               setActiveTab={setActiveTab}/> : <Spinner/>}
        </div>);
}