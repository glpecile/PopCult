import ShareMenu from "../../media/share/ShareMenu";
import {useContext, useEffect, useState} from "react";
import collaborativeService from "../../../services/CollaborativeService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import AuthContext from "../../../store/AuthContext";
import EditIcon from '@mui/icons-material/Edit';
import {useTranslation} from "react-i18next";
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import ContentCopyIcon from '@mui/icons-material/ContentCopy';
import listService from "../../../services/ListService";
import ListService from "../../../services/ListService";
import {useLocation, useNavigate} from "react-router-dom";
import {Alert, Snackbar} from "@mui/material";

const ListLowerIcons = (props) => {
    const {setErrorStatusCode} = useErrorStatus();
    const context = useContext(AuthContext);
    const username = context.username;
    const [isCollaborator, setIsCollaborator] = useState();
    const {t} = useTranslation();
    const navigate = useNavigate();
    const location = useLocation();
    const [showSnackbar, setShowSnackbar] = useState(false);
    const [error, setError] = useState(false);


    useEffect(() => {
        async function getIsCollaboratorInList() {
            try {
                const data = await collaborativeService.isListCollaborator({id: props.id, username: username});
                setIsCollaborator(data.accepted);
            } catch (error) {
                setIsCollaborator(false);
            }
        }

        if (username.localeCompare(props.owner) !== 0) {
            getIsCollaboratorInList();
        }
    }, [setErrorStatusCode, props.id, username, props.owner]);

    async function forkList() {
        if (!context.isLoggedIn) {
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            });
        }
        try {
            const listUrl = await listService.forkList(props.url);
            const data = await ListService.getList(listUrl);
            navigate('/lists/' + data.id);

            console.log(data);
        } catch (error) {
            setIsCollaborator(false);
        }
    }

    async function requestCollaboration() {
        if (!context.isLoggedIn) {
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            });
        }
        try {
            console.log(props.collaborativeRequestUrl);
            await collaborativeService.createListCollaborationRequest(props.collaborativeRequestUrl);
            setError(false);
            setShowSnackbar(true);
        } catch (error) {
            console.log(error);
            setError(true);
            setShowSnackbar(true);
        }
    }

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setShowSnackbar(false);
            }, 3000);
            return () => clearTimeout(timeOut);
        }
        , [showSnackbar]);

    return (<>
        <div className="flex flex-wrap justify-start">
            <ShareMenu isOpened={false}/>
            {(props.owner.localeCompare(username) === 0 || isCollaborator) ?
                <div className="flex justify-center py-2">
                    <button className="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded">
                        <EditIcon/>{t('lists_edit')}
                    </button>
                </div> :
                <>
                    {props.collaborative &&
                        <div className="flex justify-center py-2">
                            <button className="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded"
                                    onClick={requestCollaboration}>
                                <GroupAddIcon/><span className="pl-2">{t('lists_collaborate')}</span>
                            </button>
                        </div>}
                </>}
            {props.owner.localeCompare(username) !== 0 &&
                <div className="flex justify-end py-2">
                    <button type="submit"
                            className="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded"
                            onClick={forkList}>
                        <ContentCopyIcon/><span className="pl-2">{t('lists_fork')}</span>
                    </button>
                </div>}
        </div>
        <Snackbar open={showSnackbar} autoHideDuration={6000}
                  anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
            <Alert severity={!error? "success" : "warning"}>
                {!error? <>{t('lists_request_okay')}</> :<>{t('lists_request_error')}</>}
            </Alert>
        </Snackbar>
    </>);
}
export default ListLowerIcons;