import {Close} from "@mui/icons-material";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

const BannedUserCard = (props) => {
    const {t} = useTranslation();

    return (
        <div className="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between mt-2">
            <div className="flex">
                <img className="inline-block object-cover rounded-full h-12 w-12 mt-3.5 ml-5" alt="user_profile"
                     src={props.image}/>
                <h4 className=" pl-3 text-xl font-normal tracking-tight mt-3.5">
                    <Link className="pr-1 text-purple-500 hover:text-purple-900" to={`user/${props.username}`}>
                        <strong>{props.username}</strong>
                    </Link>
                    {t('profile_secondary_description', {username: props.username})}
                    &#8226;
                    {t('profile_strikes', {strikes: props.strikes})}
                    <p className="text-sm text-red-400">
                        {t('profile_unbanDate', {date: props.unbanDate})}
                    </p>
                </h4>
            </div>
            <div className="flex justify-between p-3 text-center justify-center items-center">
                <button onClick={ () => {
                    props.unbanUser(props.username);
                }}>
                    <Close/>
                </button>
            </div>
        </div>
    );
}
export default BannedUserCard;