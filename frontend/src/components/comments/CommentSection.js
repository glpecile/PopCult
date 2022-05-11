import CommentList from "./CommentList";
import NewComment from "./NewComment";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {Alert, Snackbar} from "@mui/material";

const CommentSection = (props) => {
    const {t} = useTranslation();
    const [commentsUpdate, setCommentsUpdate] = useState(0);
    const [showAlert, setShowAlert] = useState(false);
    const [status, setStatus] = useState(0);

    function showReportAlert(data) {
        setShowAlert(true);
        setStatus(data);
    }

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setShowAlert(false);
            }, 3000);
            return () => clearTimeout(timeOut);
        }
        , [showAlert]);

    return (
        <>
            <div className="flex flex-col bg-white shadow-md rounded-lg p-3 pt-0 mt-3">
                <div className="text-xl text-justify max-w-full break-words pt-2">{t('comments_title')}</div>
                <div className="px-2">
                    <NewComment commentsUrl={props.commentsUrl} setCommentsUpdate={setCommentsUpdate}/>
                    <CommentList commentsUrl={props.commentsUrl} commentsUpdate={commentsUpdate}
                                 setCommentsUpdate={setCommentsUpdate} showReportAlert={showReportAlert}/>
                </div>

            </div>
            <Snackbar open={showAlert} autoHideDuration={6000}
                      anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                <Alert severity="success">
                    {status === 201 ? <>{t('report_success')} </> : <>{t('report_admin_success')}</>}
                </Alert>
            </Snackbar>
        </>
    );
}
export default CommentSection;