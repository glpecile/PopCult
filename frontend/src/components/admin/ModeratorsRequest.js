import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import OneButtonDialog from "../modal/OneButtonDialog";

const ModeratorsRequest = (user) => {
    const {t} = useTranslation();

    const acceptRequest = () => {
        user.acceptModerator(user.url);
    }
    const rejectRequest = () => {
        user.rejectModerator(user.url);
    }
    return (<div className="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
        <div className="flex">
            <img className="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="profile_image"
                 src={user.image}/>
            <h4 className="pl-3 py-4 text-xl font-normal tracking-tight">
                <Link to={`user/${user.username}`} className="text-purple-500 hover:text-purple-900 pr-2">
                    <strong>
                        {user.username}
                    </strong>
                </Link>
                {t('profile_secondary_description', {username: user.username})}
            </h4>
        </div>
        <div className="flex justify-between m-3.5  justify-center text-center">
            <div className="pr-2">
                <OneButtonDialog
                    buttonClassName="btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
                    buttonIcon={<i className="fas fa-check group-hover:text-white mr-2"/>}
                    buttonText={t("moderator_accept")}
                    title={t("moderator_accept")}
                    body={t('moderator_accept_body', {username: user.username})}
                    actionTitle={t('accept')}
                    onActionAccepted={acceptRequest}
                    isOpened={false}/>
            </div>
            <OneButtonDialog
                buttonClassName="btn btn-danger bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
                buttonIcon={<i className="fas fa-times group-hover:text-white mr-2"/>}
                buttonText={t("moderator_reject")}
                title={t("moderator_reject")}
                body={t('moderator_reject_body', {username: user.username})}
                actionTitle={t('reject')}
                onActionAccepted={rejectRequest}
                isOpened={false}/>
        </div>
    </div>);
}
export default ModeratorsRequest;