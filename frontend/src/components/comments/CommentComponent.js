import {useCallback, useContext, useEffect, useState} from "react";
import UserService from "../../services/UserService";
import {Link} from "react-router-dom";
import {Alert, ListItem, Snackbar} from "@mui/material";
import {Close} from "@mui/icons-material";
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import AuthContext from "../../store/AuthContext";
import useErrorStatus from "../../hooks/useErrorStatus";
import commentService from "../../services/CommentService";
import OneButtonDialog from "../modal/OneButtonDialog";
import {useTranslation} from "react-i18next";
import reportService from "../../services/ReportService";
import FormDialog from "../modal/FormDialog";
import {CommentSectionType} from "../../enums/CommentSectionType";

const CommentComponent = (props) => {
    const {t} = useTranslation();

    const [user, setUser] = useState(undefined);
    const [comment, setComment] = useState(undefined);
    const [reportBody, setReportBody] = useState('');
    const context = useContext(AuthContext);
    const currentUser = context.username;

    const [showAlert, setShowAlert] = useState(false);
    const [status, setStatus] = useState(0);
    const [error, setError] = useState(false);

    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        setComment(props.comment);
    }, [props.comment]);

    useEffect(() => {
        if (comment) {
            async function getUserData() {
                try {
                    const data = await UserService.getUser(comment.userUrl);
                    setUser(data);
                } catch (error) {
                    setErrorStatusCode(error.response.status);

                }
            }

            getUserData();
        }
    }, [comment, setErrorStatusCode]);

    async function deleteComment() {
        if (props.type === CommentSectionType.LISTS) {
            try {
                await commentService.deleteListComment(comment.url);
                props.setCommentsUpdate(prev => prev + 1);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        } else {
            try {
                await commentService.deleteMediaComment(comment.url);
                props.setCommentsUpdate(prev => prev + 1);

            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }
    }

    function handleReport(event) {
        const aux = event.target.value;
        if (!(aux.includes('<') || aux.includes('>'))) setReportBody(aux);
    }

    const createReport = useCallback(async () => {
        if (props.type === CommentSectionType.LISTS)
            return await reportService.createListCommentReport({
                url: comment.reportsUrl,
                data: reportBody
            });//data.status
        else
            return await reportService.createMediaCommentReport({
                url: comment.reportsUrl,
                data: reportBody
            })
    }, [comment, props.type, reportBody]);

    async function submitReport(event) {
        console.log(comment)
        event.preventDefault();
        try {
            const data = await createReport();
            if (data.status === 204) {
                props.setCommentsUpdate(prev => prev + 1);
                setStatus(data.status)
                setShowAlert(true);
            } else if (data.status === 201) {
                setStatus(data.status)
                setShowAlert(true);
                setError(false);
            }
        } catch (error) {
            setStatus(error.response.status)
            setShowAlert(true);
            setError(true);
        }
    }

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setShowAlert(false);
            }, 3000);
            return () => clearTimeout(timeOut);
        }
        , [showAlert]);

    return (
        <>{(comment && user) &&
            <ListItem className="p-1 my-2 ring-2 ring-gray-200 bg-white rounded-lg flex items-start">
                <img className="inline-block object-cover rounded-full h-14 w-14 m-2" alt="profile_image" src={user.imageUrl}/>
                <div className="flex-col w-full">
                    <div className="flex items-center text-lg justify-between">
                        <div className="flex">
                            <Link className="text-decoration-none text-violet-500 hover:text-violet-900"
                                  to={'/user/' + user.username}>{user.username}</Link>
                            <div className="text-base tracking-tight pl-1 text-gray-400">
                                &#8226;
                            </div>
                            <div className="text-base tracking-tight pl-1 text-gray-400">
                                {(comment.creationDate).slice(0, 10)}
                            </div>
                        </div>
                        <div className="flex">
                            {
                                // delete and report
                                (user && currentUser && (currentUser.localeCompare(user.username) === 0)) ?
                                    <OneButtonDialog
                                        buttonClassName="text-red-500 hover:text-red-900 m-1 h-min w-min"
                                        buttonIcon={<Close className="mb-1 align-top"/>}
                                        title={t('delete_comment_title')}
                                        body={t('delete_comment_body')}
                                        actionTitle={t('delete_comment_confirmation')}
                                        onActionAccepted={deleteComment}
                                        submitButtonClassName="btn btn-link btn-rounded text-red-500 hover:text-red-900"
                                        isOpened={false}/> :
                                    <FormDialog
                                        tooltip={t('report_content')}
                                        buttonClassName="text-amber-500 hover:text-amber-700 m-1 h-min w-min"
                                        buttonIcon={<ErrorOutlineIcon className="mb-1 align-top"/>}
                                        title={t('report_comment_title')}
                                        body={t('report_comment_body')}
                                        submitReport={submitReport}
                                        actionTitle={t('report_submit')}
                                        reportBody={reportBody}
                                        handleReport={handleReport}
                                        submitButtonClassName="text-amber-500 hover:text-amber-700"
                                        isOpened={false}/>
                            }
                        </div>
                    </div>
                    <div className="m-0 pb-2 max-w-full break-words">
                        {
                            comment.commentBody
                        }
                    </div>
                </div>
            </ListItem>}
            <Snackbar open={showAlert} autoHideDuration={6000}
                      anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                <Alert severity={!error? "success" : "warning"}>
                    {status === 201 ? <>{t('report_success')} </> : status === 204 ? <>{t('report_admin_success')}</> : <>{t('report_error')}</>}
                </Alert>
            </Snackbar>
        </>
    );
}
export default CommentComponent;