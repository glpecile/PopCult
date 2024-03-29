import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import ClearIcon from "@mui/icons-material/Clear";
import OneButtonDialog from "../modal/OneButtonDialog";

const BannedUserCard = (props) => {
    const {t} = useTranslation();
    const banDays = Math.ceil((new Date(props.unbanDate).getTime() - new Date().getTime()) / (1000 * 3600 * 24))

    return (
        <div className="w-full h-min-20 flex-wrap bg-white overflow-hidden rounded-lg shadow-md flex justify-evenly lg:justify-between mt-2 transition duration-300 ease-in-out hover:bg-violet-50/50 hover:shadow-indigo-500/50 relative">
            <div className="flex">
                <img className="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="user_profile"
                     src={props.image}/>
                <h4 className="pl-3 text-xl font-normal tracking-tight mt-3.5">
                    <Link className="pr-1 text-violet-500 hover:text-violet-900" to={`user/${props.username}`}>
                        <strong>{props.username}</strong>
                    </Link>
                    {t('profile_secondary_description', {username: props.username})}
                    &#8226;
                    {t('profile_strikes', {strikes: props.strikes})}
                    <p className="text-sm text-red-600">
                        {t('profile_unbanDate', {count: banDays})}
                    </p>
                </h4>
            </div>
            <div className="flex justify-center items-center">
                <OneButtonDialog
                    buttonClassName="btn btn-link btn-rounded text-red-500 hover:text-red-900 h-min flex items-center"
                    buttonIcon={<ClearIcon fontSize="small" className="group-hover:text-white mr-2"/>}
                    buttonText={t("unban_user")}
                    title={t('unban_user')}
                    body={t('unban_user_name', {username: props.username})}
                    actionTitle={t('unban')}
                    onActionAccepted={() => {
                        props.unbanUser(props.url)
                    }}
                    submitButtonClassName="btn btn-link btn-rounded text-red-500 hover:text-red-900"
                    isOpened={false}/>
            </div>
        </div>
    );
}
export default BannedUserCard;