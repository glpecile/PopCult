import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import Filters from "../../../components/search/Filters";
import SearchResults from "../../../components/search/SearchResults";
import {useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import mediaService from "../../../services/MediaService";
import listService from "../../../services/ListService";
import Spinner from "../../../components/animation/Spinner";

export default function SearchPage() {
    const [searchParams] = useSearchParams();
    const term = searchParams.get("term");

    const navigate = useNavigate();
    const [media, setMedia] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [mediaPage, setMediaPage] = useState(searchParams.get("mp") || 1);
    const [listPage, setListPage] = useState(searchParams.get("lp") || 1);
    const [activeTab, setActiveTab] = useState(0); //use this in order to know which options of filters to give
    const pageSize = 4;

    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        navigate({
            pathname: '/search',
            search: createSearchParams({
                term: term,
                mp: mediaPage,
                lp: listPage
            }).toString()
        });
    }, [mediaPage, listPage, navigate, term])


    useEffect(() => {
        async function getMedia() {
            try {
                const data = await mediaService.getMediaList({page: mediaPage, pageSize: pageSize, query: term})
                setMedia(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getMedia();
    }, [term, mediaPage, setErrorStatusCode]);

    useEffect(() => {
        async function getLists() {
            try {
                const data = await listService.getLists({page: listPage, pageSize: pageSize, query: term})
                setLists(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getLists();
    }, [term, listPage, setErrorStatusCode]);

    return (
        <div>
            Showing results for: {term}
            <Filters/>
            {(media && lists) ?
                <SearchResults media={media} mediaPage={mediaPage} setMediaPage={setMediaPage} lists={lists}
                               listPage={listPage} setListPage={setListPage} activeTab={activeTab}
                               setActiveTab={setActiveTab}/> : <Spinner/>}
        </div>);
}