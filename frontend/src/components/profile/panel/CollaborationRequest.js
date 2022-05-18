import {Link} from "react-router-dom";
import {Trans, useTranslation} from "react-i18next";
import OneButtonDialog from "../../modal/OneButtonDialog";
import CheckOutlinedIcon from "@mui/icons-material/CheckOutlined";
import {Close} from "@mui/icons-material";
import collaborativeService from "../../../services/CollaborativeService";
import useErrorStatus from "../../../hooks/useErrorStatus";

const CollaborationRequest = (props) => {
    const {t} = useTranslation();
    const {setErrorStatusCode} = useErrorStatus();

    const acceptRequest = async () => {
        try {
            await collaborativeService.acceptCollaborationRequest(props.url);
            props.refresh();
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }

    const rejectRequest = async () => {
        try {
            await collaborativeService.deleteCollaborationRequest(props.url);
            props.refresh();
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }

    return (<div
        className={"my-1 w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107 " + props.className}>
        <div className="flex items-center">
            <h4 className="text-base pl-3 py-4 text-xl font-normal tracking-tight">
                <Trans i18nKey="panel_collaborators">
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/user/${props.username}`}>{{username: props.username}}</Link>
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/lists/${props.listId}`}>{{list: props.listname}}</Link>
                </Trans>
            </h4>
        </div>
        <div className="flex justify-between p-3 text-center justify-center items-center">
            <OneButtonDialog
                buttonClassName="text-green-500 hover:text-green-900 m-1 h-min w-min"
                buttonIcon={<CheckOutlinedIcon className="mb-1 align-top"/>}
                title={t("collaborator_accept")}
                body={t('collaborator_accept_body', {username: props.listname})}
                actionTitle={t('accept')}
                onActionAccepted={acceptRequest}
                isOpened={false}/>
            {/* TODO: check style */}
            <OneButtonDialog
                buttonClassName="text-red-500 hover:text-red-900 m-1 h-min w-min"
                buttonIcon={<Close className="mb-1 align-top"/>}
                title={t('collaborator_reject')}
                body={t('collaborator_reject_body', {username: props.username})}
                actionTitle={t('reject')}
                onActionAccepted={rejectRequest}
                submitButtonClassName="text-red-500 hover:text-red-900"
                isOpened={false}/>
        </div>
    </div>)
}
export default CollaborationRequest;