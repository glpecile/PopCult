import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useState} from "react";
import {Navigate} from "react-router-dom";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";

const DUMMY_USER = {
    userid: 1,
    image: "https://pbs.twimg.com/media/Ec5mKYbUMAAKx7A.jpg",
    name: "Gengar Gengar",
    username: "Genga",
};

const Settings = () => {
    const [successfulUpdate, setSuccessfulUpdate] = useState(false);
    const {t} = useTranslation();
    const updateUserData = (props) => {
        setSuccessfulUpdate(true);
    }
    return (
        <>
            <Helmet>
                <title>{t('settings_title')}</title>
            </Helmet>
            <SettingsUserProfile name={DUMMY_USER.name} username={DUMMY_USER.username} image={DUMMY_USER.image}
                                 onSaveUserData={updateUserData}/>
            {successfulUpdate && <Navigate to='/user/a'/>}
        </>
    );
}
export default Settings;