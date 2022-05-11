import {Link} from "react-router-dom";
import {Trans, useTranslation} from "react-i18next";
import {IconButton} from "@mui/material";
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';

const UserProfile = (user) => {
    const {t} = useTranslation();
    const isCurrentUser = user.isCurrentUser;
    const username = user.username;

    return (
        <div className="flex flex-col gap-3 justify-center items-center">
            <div className="relative inline-block">
                <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                     src={user.image}/>
            </div>
            <div className="flex items-center space-x-1">
                <h2 className="text-3xl font-bold mb-0">
                    {user.name}
                </h2>
                {
                    isCurrentUser &&
                    (<Link to='/settings'>
                        <IconButton size={"small"} title={t('profile_edit')}>
                            <SettingsOutlinedIcon className="text-violet-500 hover:text-violet-900"/>
                        </IconButton>
                    </Link>)
                }
            </div>
            <h4 className="text-base">
                <Trans i18nKey="profile_description">
                    <b>{{username}}</b>
                </Trans>
            </h4>
        </div>);
}

export default UserProfile;