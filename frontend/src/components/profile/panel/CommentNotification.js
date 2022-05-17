import {Link} from "react-router-dom";
import {Trans} from "react-i18next";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import useErrorStatus from "../../../hooks/useErrorStatus";
import notificationService from "../../../services/NotificationService";

const CommentNotification = (props) => {
    const {setErrorStatusCode} = useErrorStatus();

    const setRead = async () => {
        try {
            await notificationService.setNotificationAsOpened(props.url)
            props.refresh();
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }

    const deleteNotification = async () => {
        try {
            await notificationService.deleteNotification(props.url);
            props.refresh();
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }
    return <div
        className={"my-2 w-full h-30 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107 relative" + props.className}>
        <div className="flex flex-col inline-flex">
            <h4 className="text-base pl-3 py-4 text-xl font-normal tracking-tight">
                <Trans i18nKey="notification_comment_title">
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/user/${props.username}`}>{{username: props.username}}</Link>
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/lists/${props.listId}`}>{{list: props.listname}}</Link>
                </Trans>
            </h4>
            <h4 className="text-base pl-3 py-4 tracking-tight flex flex-wrap">
                {props.commentBody}
            </h4>
            {!props.opened &&
                <span className="absolute h-3 w-3 top-0 right-0 mt-2 mr-3" onClick={setRead}>
                            <span
                                className="animate-ping absolute inline-flex h-full w-full rounded-full bg-violet-400 opacity-75 mt-1"></span>
                            <span className="relative inline-flex rounded-full h-3 w-3 bg-violet-500 mb-0.5"></span>
                </span>}
        </div>
        <div className="flex justify-end items-end mr-2 mb-2">
            <button onClick={deleteNotification}><DeleteForeverIcon className="text-violet-500 hover:text-violet-900 mb-1 mr-1"/></button>
        </div>
    </div>;
}
export default CommentNotification;