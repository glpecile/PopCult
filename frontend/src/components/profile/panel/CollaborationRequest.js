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
        className={"my-1 w-full h-min-20 flex-wrap bg-white overflow-hidden rounded-lg shadow-md flex justify-evenly lg:justify-between transition duration-300 ease-in-out hover:bg-violet-50/50 hover:shadow-indigo-500/50 relative " + props.className}>
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
        <div className="flex justify-end items-center mr-2 mb-2 space-x-2">
            <OneButtonDialog
                buttonClassName="btn btn-link btn-rounded text-violet-500 hover:text-violet-900 h-min flex items-center"
                buttonIcon={<CheckOutlinedIcon fontSize="small" className="group-hover:text-white mr-2"/>}
                buttonText={t("accept")}
                title={t("collaborator_accept")}
                body={t('collaborator_accept_body', {username: props.listname})}
                actionTitle={t('accept')}
                onActionAccepted={acceptRequest}
                submitButtonClassName="btn btn-link btn-rounded text-violet-500 hover:text-violet-900"
                isOpened={false}/>
            {/* TODO: check style */}
            <OneButtonDialog
                buttonClassName="btn btn-link btn-rounded text-amber-500 hover:text-amber-900 h-min flex items-center"
                buttonIcon={<Close fontSize="small" className="group-hover:text-white mr-2"/>}
                buttonText={t("reject")}
                title={t('collaborator_reject')}
                body={t('collaborator_reject_body', {username: props.username})}
                actionTitle={t('reject')}
                onActionAccepted={rejectRequest}
                submitButtonClassName="btn btn-link btn-rounded text-amber-500 hover:text-amber-900"
                isOpened={false}/>
        </div>
    </div>)
}
export default CollaborationRequest;