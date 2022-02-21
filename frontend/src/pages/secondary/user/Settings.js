import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useContext, useEffect, useRef, useState} from "react";
import {Navigate} from "react-router-dom";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import UserContext from "../../../store/UserContext";
import AuthContext from "../../../store/AuthContext";


const Settings = () => {
    const [successfulUpdate, setSuccessfulUpdate] = useState(false);
    const {t} = useTranslation();
    const authContext = useContext(AuthContext);
    const userContext = useContext(UserContext);
    const mountedUser = useRef(true);
    const getUser = userContext.getUser;
    const username = authContext.username;
    const [userData, setUserData] = useState('');

    useEffect(() => {
        mountedUser.current = true;
        getUser(mountedUser, username, setUserData);
        return () => {
            mountedUser.current = false
        };
    }, [username, getUser]);

    const updateUserData = (props) => {
        setSuccessfulUpdate(true);
    }
    return (
        <>
            <Helmet>
                <title>{t('settings_title')}</title>
            </Helmet>
            <SettingsUserProfile name={userData.name} username={userData.username} image={' '}
                                 onSaveUserData={updateUserData}/>
            {successfulUpdate && <Navigate to={'/user/'+userData.username}/>}
        </>
    );
}
export default Settings;