import MediaInList from "../MediaInList";
import PaginationComponent from "../../PaginationComponent";
import Spinner from "../../animation/Spinner";
import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import {useTranslation} from "react-i18next";

const ListMedia = (props) => {
    const {t} = useTranslation();

    const [page, setPage] = useState(1);
    const pageSize = 4;
    const {setErrorStatusCode} = useErrorStatus();
    const [mediaInlist, setMediaInList] = useState(undefined);

    useEffect(() => {
        async function getListMedia() {
            try {
                const media = await ListService.getMediaInList({url: props.mediaUrl, page, pageSize});
                setMediaInList(media);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getListMedia();
    }, [props.mediaUrl, page, pageSize, setErrorStatusCode])

    return <>{(mediaInlist) ? (<>{mediaInlist.data.length > 0 ? <>
            <MediaInList media={mediaInlist.data}/>
            <div className="flex justify-center pt-4">
                {(mediaInlist.data.length > 0 && mediaInlist.links.last.page > 1) &&
                    <PaginationComponent page={page} lastPage={mediaInlist.links.last.page}
                                         setPage={setPage}/>}
            </div>
        </> : <div className="text-gray-400 flex justify-center py-2">{t('profile_tabs_noContent')}</div>}
        </>) :
        <div className="flex justify-center"><Spinner/></div>}</>;
}
export default ListMedia;