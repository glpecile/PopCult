import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import Loader from "../errors/Loader";
import MediaInList from "../../../components/lists/MediaInList";
import Spinner from "../../../components/animation/Spinner";
import {Avatar, Chip} from "@mui/material";
import CollaborativeService from "../../../services/CollaborativeService";
import PaginationComponent from "../../../components/PaginationComponent";
import CommentSection from "../../../components/comments/CommentSection";
import useErrorStatus from "../../../hooks/useErrorStatus";

function ListsDescription() {
    const id = window.location.pathname.split('/')[2];
    const [list, setList] = useState(undefined);
    const [mediaInlist, setMediaInList] = useState(undefined);
    const [collabInlist, setCollabInList] = useState(undefined);
    const [page, setPage] = useState(1);
    const pageSize = 4;
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getList(id) {
            try {
                const data = await ListService.getListById(id);
                setList(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getList(id);
    }, [id, setErrorStatusCode]);

    useEffect(() => {
        if (list) {
            async function getListMedia() {
                try {
                    const media = await ListService.getMediaInList({url: list.mediaUrl, page, pageSize});
                    setMediaInList(media);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }

            getListMedia();
        }
    }, [list, page, pageSize, setErrorStatusCode])

    useEffect(() => {
        if (list) {
            async function getListCollaborators() {
                try {
                    const collabs = await CollaborativeService.getListCollaborators({
                        url: list.collaboratorsUrl,
                        page,
                        pageSize
                    });
                    setCollabInList(collabs);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }

            getListCollaborators();
        }
    }, [list, page, pageSize, setErrorStatusCode])

    return (<>
        {list ? (<>
            <div className="flex flex-wrap pt-2">
                <h2 className="display-5 fw-bolder">
                    {list.name}
                </h2>
                {/*    report and favorite buttons */}
            </div>
            {/*    list author and forking info*/}
            {/*    forked from*/}
            {/*amount of forks*/}
            <p className="lead text-justify max-w-full break-words pb-2">
                {list.description}
            </p>
            {/*    collaborators */}
            {(collabInlist && collabInlist.data.length > 0) && (<>
                {collabInlist.data.map((user) => {
                    return <Chip
                        label={user.username}
                        variant="outlined" color="secondary"
                        avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                        key={user.username}
                    />
                })}
            </>)}
            {/*    share and edit */}
            {(mediaInlist && mediaInlist.data.length > 0) ? (<>
                    <MediaInList media={mediaInlist.data}/>
                    <div className="flex justify-center">
                        {(mediaInlist.data.length > 0 && mediaInlist.links.last.page > 1) &&
                            <PaginationComponent page={page} lastPage={mediaInlist.links.last.page}
                                                 setPage={setPage}/>}
                    </div>
                </>) :
                <div className="flex justify-center"><Spinner/></div>}
            <CommentSection commentsUrl={list.commentsUrl}/>
        </>) : <Loader/>}
    </>);
}

export default ListsDescription;