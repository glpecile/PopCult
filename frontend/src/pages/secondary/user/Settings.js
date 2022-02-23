import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useContext, useEffect, useRef, useState} from "react";
import {Navigate} from "react-router-dom";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import UserService from "../../../services/UserService";
import AuthContext from "../../../store/AuthContext";
import * as PropTypes from "prop-types";
import Loader from "../errors/Loader";


function Suspense(props) {
    return null;
}

Suspense.propTypes = {
    fallback: PropTypes.any,
    children: PropTypes.node
};

const Settings = () => {
    const [successfulUpdate, setSuccessfulUpdate] = useState(false);
    const {t} = useTranslation();
    const mountedUser = useRef(true);
    const [toEditData, settoEditData] = useState(undefined);
    const [userData, setUserData] = useState(undefined);
    const username = useRef(useContext(AuthContext)).current.username;

    const getUser = async () => {
        if (username) {
            try {
                const user = await UserService.getUser(username);
                setUserData(user);
            } catch (error) {
                console.log(error);
            }
        }
    }

    useEffect(() => {
        getUser();
    }, [])

    const editUser = async () => {
        if (toEditData !== undefined) {
            try {
                await UserService.editUser({username, name: toEditData.name});
            } catch (error) {
                console.log(error);
            }
            if (toEditData.imageUrl !== undefined) {
                try {
                    let formData = new FormData();
                    formData.append('image', toEditData.imageUrl);
                    await UserService.uploadUserImage({username, formData});
                } catch (error) {
                    console.log(error);
                }
            }
            setSuccessfulUpdate(true);
        }
    }

    useEffect(() => {
        editUser();
    }, [toEditData]);

    const updateUserData = (props) => {
        settoEditData(props);
    }
    return (
        <>
            <Helmet>
                <title>{t('settings_title')}</title>
            </Helmet>
            {userData === undefined && <Loader/>}
            {userData !== undefined && <>
                <SettingsUserProfile name={userData.name} username={userData.username} image={userData.imageUrl}
                                     email={userData.email}
                                     onSaveUserData={updateUserData}/>
                {successfulUpdate && <Navigate to={'/user/' + userData.username}/>}
            </>
            }
        </>
    );
}
export default Settings;