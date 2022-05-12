import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import Loader from "../errors/Loader";
import CommentSection from "../../../components/comments/CommentSection";
import useErrorStatus from "../../../hooks/useErrorStatus";
import ListCollaborators from "../../../components/lists/description/ListCollaborators";
import ListMedia from "../../../components/lists/description/ListMedia";
import {useTranslation} from "react-i18next";
import ListUpperIcons from "../../../components/lists/description/ListUpperIcons";
import {Alert,Snackbar} from "@mui/material";
import {Link, useLocation} from "react-router-dom";
import ListLowerIcons from "../../../components/lists/description/ListLowerIcons";
import ListForks from "../../../components/lists/description/ListForks";

function ListsDescription() {
    const location = useLocation();
    const id = location.pathname.split('/')[2];
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
                <ListUpperIcons owner={list.owner} favoriteUrl={list.favoriteUrl} reportsUrl={list.reportsUrl}
                                openAlert={showSnackbar}/>
            </div>
            {/*    list author and forking info*/}
            <div className="flex justify-right">
                {t('list_by')}<Link className="text-violet-500 hover:text-violet-900 font-bold" to={list.ownerUrl}>{list.owner}</Link>
                {list.forkedFrom && <>{t('forked_from')}<Link className="text-violet-500 hover:text-violet-900 font-bold" to={list.forkedFromUrl}>{list.forkedFrom}</Link></>}
                <ListForks forksUrl={list.forksUrl}/>
            </div>
            <p className="lead text-justify max-w-full break-words pb-2">
                {list.description}
            </p>
            {/* collaborators */}
            <ListCollaborators collaboratorsUrl={list.collaboratorsUrl}/>
            {/* share edit and fork */}
            <ListLowerIcons id={list.id} collaborative={list.collaborative} owner={list.owner} url={list.forksUrl}/>
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