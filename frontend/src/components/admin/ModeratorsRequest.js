import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import OneButtonDialog from "../modal/OneButtonDialog";
import CheckOutlinedIcon from '@mui/icons-material/CheckOutlined';
import ClearIcon from '@mui/icons-material/Clear';

const ModeratorsRequest = (user) => {
    const {t} = useTranslation();

    const acceptRequest = () => {
        user.acceptModerator(user.url);
    }
    const rejectRequest = () => {
        user.rejectModerator(user.url);
    }
    return (<div className="w-full h-min-20 flex-wrap bg-white overflow-hidden rounded-lg shadow-md flex justify-evenly lg:justify-between mt-2">
        <div className="flex">
            <img className="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="profile_image"
                 src={user.image}/>
            <h4 className="pl-3 py-4 text-xl font-normal tracking-tight">
                <Link to={`user/${user.username}`} className="text-violet-500 hover:text-violet-900 pr-2">
                    <strong>
                        {user.username}
                    </strong>
                </Link>
                {t('profile_secondary_description', {username: user.username})}
            </h4>
        </div>
        <div className="flex justify-between m-3.5 justify-center text-center items-center">
            <div className="pr-2">
                <OneButtonDialog
                    buttonClassName="btn btn-link btn-rounded text-violet-500 hover:text-violet-900 h-min flex items-center"
                    buttonIcon={<CheckOutlinedIcon fontSize="small" className="group-hover:text-white mr-2"/>}
                    buttonText={t("moderator_accept")}
                    title={t("moderator_accept")}
                    body={t('moderator_accept_body', {username: user.username})}
                    actionTitle={t('accept')}
                    onActionAccepted={acceptRequest}
                    submitButtonClassName="btn btn-link btn-rounded text-violet-500 hover:text-violet-900"
                    isOpened={false}/>
            </div>
            <OneButtonDialog
                buttonClassName="btn btn-link btn-rounded text-amber-500 hover:text-amber-900 h-min flex items-center"
                buttonIcon={<ClearIcon fontSize="small" className="group-hover:text-white mr-2"/>}
                buttonText={t("moderator_reject")}
                title={t("moderator_reject")}
                body={t('moderator_reject_body', {username: user.username})}
                actionTitle={t('reject')}
                onActionAccepted={rejectRequest}
                submitButtonClassName="btn btn-link btn-rounded text-amber-500 hover:text-amber-900"
                isOpened={false}/>
        </div>
    </div>);
}
export default ModeratorsRequest;