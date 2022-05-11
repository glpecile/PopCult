import {useContext, useEffect, useState} from "react";
import UserService from "../../services/UserService";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {ListItem} from "@mui/material";
import {Close} from "@mui/icons-material";
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import AuthContext from "../../store/AuthContext";
import useErrorStatus from "../../hooks/useErrorStatus";
import commentService from "../../services/CommentService";
import OneButtonDialog from "../modal/OneButtonDialog";
import {useTranslation} from "react-i18next";
import reportService from "../../services/ReportService";
import FormDialog from "../modal/FormDialog";

const CommentComponent = (props) => {
    const {t} = useTranslation();

    const [user, setUser] = useState(undefined);
    const [comment, setComment] = useState(undefined);
    const [reportBody, setReportBody] = useState('');
    const context = useContext(AuthContext);
    const currentUser = context.username;

    const {setErrorStatusCode} = useErrorStatus();
    const location = useLocation()
    const navigate = useNavigate();

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
        try {
            await commentService.deleteListComment(comment.url);
            props.setCommentsUpdate(prev => prev - 1);
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }

    function handleReport(event) {
        const aux = event.target.value;
        if (!(aux.includes('<') || aux.includes('>'))) setReportBody(aux);
    }

    async function submitReport(event) {
        event.preventDefault();
        if (!context.isLoggedIn) {
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            })
        }
        try {
            const data = await reportService.createListCommentReport(comment.url);
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }

    return (
        <>{(comment && user) &&
            <ListItem className="p-1 my-2 ring-2 ring-gray-200 bg-white rounded-lg flex flex-wrap flex-col">
                <div className="grid grid-cols-12 gap-2">
                    <div><img className="inline-block object-cover rounded-full" alt="profile_image"
                              src={user.imageUrl}/>
                    </div>
                    <div className="col-span-10 flex flex-row items-center text-lg">
                        <Link className="text-decoration-none text-violet-500 hover:text-violet-900"
                              to={'/user/' + user.username}>{user.username}</Link>
                        <div className="text-base tracking-tight pl-1 text-gray-400">
                            &#8226;
                        </div>
                        <div className="text-base tracking-tight pl-1 text-gray-400">
                            {(comment.creationDate).slice(0, 10)}
                        </div>
                    </div>
                    <div>
                        {/*delete and report*/}
                        {(user && currentUser && (currentUser.localeCompare(user.username)=== 0)) ?
                            <OneButtonDialog
                                buttonClassName="text-red-500 hover:text-red-900 m-1 h-min w-min"
                                buttonIcon={<Close className="mb-1 align-top"/>}
                                title={t('delete_comment_title')}
                                body={t('delete_comment_body')}
                                actionTitle={t('delete_comment_confirmation')}
                                onActionAccepted={deleteComment}
                                submitButtonClassName="text-red-500 hover:text-red-900"
                                isOpened={false}/> :
                            <FormDialog
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
                    <div/>
                    <div className=" col-span-11 flex items-center lg:pb-2">
                        <div className=" m-0 max-w-full break-words"> {comment.commentBody} </div>
                    </div>

                </div>
            </ListItem>}</>
    );
}
export default CommentComponent;