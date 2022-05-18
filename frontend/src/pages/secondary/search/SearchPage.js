import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import Filters from "../../../components/search/filters/Filters";
import SearchResults from "../../../components/search/SearchResults";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
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

    const mediaSort = 'msort';
    const mediaType = 'mtype';
    const mediaDecades = 'mdecades';
    const mediaCategories = 'mcategories';
    const listSort = 'lsort';
    const listDecades = 'ldecades';
    const listCategories = 'lcategories';

    const navigate = useNavigate();
    const genres = useContext(GenresContext).genres;
    const [media, setMedia] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [mediaPage, setMediaPage] = useState(searchParams.get("mp") || 1);
    const [listPage, setListPage] = useState(searchParams.get("lp") || 1);
    const [activeTab, setActiveTab] = useState(0); //use this in order to know which options of filters to give

    const pageSize = 12;
    const {setErrorStatusCode} = useErrorStatus();
    let firstLoad = useRef(true);


    const getMediaFilters = useCallback(() => {
        const aux = new Map();
        if (searchParams.has(mediaCategories)) aux.set(mediaCategories, searchParams.getAll(mediaCategories));
        if (searchParams.has(mediaType)) aux.set(mediaType, searchParams.get(mediaType));
        if (searchParams.has(mediaDecades)) aux.set(mediaDecades, searchParams.get(mediaDecades));
        if (searchParams.has(mediaSort)) aux.set(mediaSort, searchParams.get(mediaSort));
        return aux;
    }, [searchParams]);

    const getListFilters = useCallback(() => {
        const aux = new Map();
        if (searchParams.has(listCategories)) aux.set(listCategories, searchParams.getAll(listCategories));
        if (searchParams.has(listDecades)) aux.set(listDecades, searchParams.get(listDecades));
        if (searchParams.has(listSort)) aux.set(listSort, searchParams.get(listSort));
        return aux;
    }, [searchParams]);

    const [mediaFilters, setMediaFilters] = useState(getMediaFilters);
    const [listFilters, setListFilters] = useState(getListFilters);

    useEffect(() => {
        firstLoad.current = true;
        setMediaPage(searchParams.get("mp"));
        setListPage(searchParams.get("lp"));
        setMediaFilters(getMediaFilters)
        setListFilters(getListFilters)
    }, [searchParams, getMediaFilters, getListFilters])

    useEffect(() => {
        if (firstLoad.current !== true)
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
        firstLoad.current = false;
    }, [term, mediaPage, listPage, mediaFilters, listFilters, navigate]);


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
            />}
            {(media && lists) ?
                <SearchResults media={media} mediaPage={mediaPage} setMediaPage={setMediaPage} lists={lists}
                               listPage={listPage} setListPage={setListPage} activeTab={activeTab}
                               setActiveTab={setActiveTab}/> : <Spinner/>}
        </div>);
}