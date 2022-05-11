import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import Loader from "../errors/Loader";
import CommentSection from "../../../components/comments/CommentSection";
import useErrorStatus from "../../../hooks/useErrorStatus";
import ListCollaborators from "../../../components/lists/description/ListCollaborators";
import ListMedia from "../../../components/lists/description/ListMedia";
import {useTranslation} from "react-i18next";
import ListUpperIcons from "../../../components/lists/description/ListUpperIcons";
import {Alert, Snackbar} from "@mui/material";

function ListsDescription() {
    const id = window.location.pathname.split('/')[2];
    const [list, setList] = useState(undefined);
    const [snackbar, setSnackbar] = useState(false);
    const {t} = useTranslation();

    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getList(id) {
            try {
                const data = await ListService.getListById(id);
                setList(data);
                console.log(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getList(id);
    }, [id, setErrorStatusCode]);

    function showSnackbar() {
        setSnackbar(true);
    }

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setSnackbar(false);
            }, 3000);
            return () => clearTimeout(timeOut);
        }
        , [snackbar]);

    return (<>
        {list ? (<>
            <div className="flex flex-wrap pt-2">
                <h2 className="display-5 fw-bolder">
                    {list.name}
                </h2>
                <ListUpperIcons owner={list.owner} favoriteUrl={list.favoriteUrl} reportsUrl={list.reportsUrl} openAlert={showSnackbar}/>
            </div>
            {/*    list author and forking info*/}
            {/*    forked from*/}
            {/*amount of forks*/}
            <p className="lead text-justify max-w-full break-words pb-2">
                {list.description}
            </p>
            {/*    collaborators */}
            <ListCollaborators collaboratorsUrl={list.collaboratorsUrl}/>
            {/*    share and edit */}
            <ListMedia mediaUrl={list.mediaUrl}/>
            <CommentSection commentsUrl={list.commentsUrl}/>
        </>) : <Loader/>}
        <Snackbar open={snackbar} autoHideDuration={6000}
                  anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
            <Alert severity="success">
                {t('report_success')}
            </Alert>
        </Snackbar>
    </>);
}

export default ListsDescription;