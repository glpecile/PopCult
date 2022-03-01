import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Close} from "@mui/icons-material";
import OneButtonDialog from "../modal/OneButtonDialog";

const ModeratorCard = (moderator) => {
    const {t} = useTranslation();

    const removeMod = () => {
        moderator.removeModerator(moderator.username);
    }

    return (<div className="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
        <div className="flex">
            <img className="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="profile_image"
                 src={moderator.image}/>
            <h4 className="pl-3 py-4 text-xl font-normal tracking-tight">
                <Link to={`user/${moderator.username}`} className="text-purple-500 hover:text-purple-900 pr-2">
                    <strong>
                        {moderator.username}
                    </strong>
                </Link>
                {t('profile_secondary_description', {username: moderator.username})}
            </h4>
        </div>
        <div className="flex justify-between p-3 text-center justify-center items-center">
            <OneButtonDialog
                buttonClassName="hover:text-red-400"
                buttonIcon={<Close/>}
                buttonText=''
                title={t("moderator_remove")}
                body={t('moderator_remove_body', {username: moderator.username})}
                actionTitle={t('remove')}
                onActionAccepted={removeMod}
                isOpened={false}
                submitButtonClassName="btn btn-danger bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white"/>
        </div>
    </div>);
}
export default ModeratorCard;