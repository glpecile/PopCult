import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useContext, useEffect, useRef, useState} from "react";
import {Navigate} from "react-router-dom";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import UserContext from "../../../store/UserContext";


const Settings = () => {
    const [successfulUpdate, setSuccessfulUpdate] = useState(false);
    const {t} = useTranslation();
    const userContext = useRef(useContext(UserContext));
    const userData = userContext.current.currentUser;
    const mountedUser = useRef(true);
    const [editUserData, setEditUserData] = useState('');

    useEffect(() => {
        mountedUser.current = true;
        try {
            if (editUserData.name) {
                userContext.current.editCurrentUser(editUserData.name);
                setSuccessfulUpdate(true);
            }
        } catch (error) {
            console.log(error);
        }
        return () => {
            mountedUser.current = false
        };
    }, [editUserData]);

    const updateUserData = (props) => {
        console.log(props.name);
        setEditUserData(props);
    }
    return (
        <>
            <Helmet>
                <title>{t('settings_title')}</title>
            </Helmet>
            <SettingsUserProfile name={userData.name} username={userData.username} image={userData.imageUrl}
                                 email={userData.email}
                                 onSaveUserData={updateUserData}/>
            {successfulUpdate && <Navigate to={'/user/' + userData.username}/>}
        </>
    );
}
export default Settings;