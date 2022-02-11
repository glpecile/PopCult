import {Link} from "react-router-dom";
import {Trans, useTranslation} from "react-i18next";

const UserProfile = (user) => {
    const {t} = useTranslation();
    const isCurrentUser = true;

    return (
        <div className="flex flex-col gap-3 justify-center items-center">
            <div className="relative inline-block">
                <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                     src={user.image}/>
            </div>
            <div className="flex justify-center items-center space-x-3">
                <h2 className="text-3xl font-bold">
                    {user.name}
                </h2>
                {isCurrentUser && (
                    <Link className="object-center inline-block" to='/settings'>
                        <button title={t('profile_edit')}><i className="fas fa-user-edit text-purple-500 hover:text-purple-900"/></button>
                    </Link>)}
            </div>
            <h4 className="text-base">
                <Trans i18nKey="profile_description">
                    Or as we like to call you<b>{user.username}</b>
                </Trans>
            </h4>
        </div>);
}

export default UserProfile;