import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
import {Navigate} from "react-router-dom";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import UserService from "../../../services/UserService";
import AuthContext from "../../../store/AuthContext";
import * as PropTypes from "prop-types";
import Spinner from "../../../components/animation/Spinner";


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
    const mountedEditUser = useRef(true);
    const [toEditData, setToEditData] = useState(undefined);
    const [userData, setUserData] = useState(undefined);
    const [passwordError, setPasswordError] = useState(false);
    const [deleteUser, setDeleteUser] = useState(false);
    const username = useRef(useContext(AuthContext)).current.username;
    const authContext = useContext(AuthContext);

    const getUser = useCallback(async () => {
        if (username) {
            try {
                const user = await UserService.getUser(username);
                setUserData(user);
            } catch (error) {
                console.log(error);
                setSuccessfulUpdate(true);
            }
        }
    }, [username]);

    useEffect(() => {
        mountedUser.current = true;
        getUser();
        return () => {
            mountedUser.current = false;
        }
    }, [username, getUser])


    const editUser = useCallback(async () => {
        if (toEditData !== undefined && mountedEditUser.current) {
            if ((toEditData.name).localeCompare(userData.name) !== 0) {
                try {
                    await UserService.editUser({username, name: toEditData.name});
                } catch (error) {
                    console.log(error);
                }
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
            let wrongPass = false;
            if (toEditData.password !== undefined) {
                try {
                    console.log(userData);
                    await UserService.changeUserPassword({
                        username,
                        currentPassword: toEditData.currentPassword,
                        newPassword: toEditData.password
                    });
                } catch (error) {
                    wrongPass = true;
                    setPasswordError(true);
                    console.log(error);
                }
            }
            if (!wrongPass) setSuccessfulUpdate(true);
        }
    }, [toEditData, username, userData]);

    useEffect(() => {
        mountedEditUser.current = true;
        editUser();
        return () => {
            mountedEditUser.current = false;
        }
    }, [username, editUser]);

    const deleteUserAccount = useCallback(async () => {
        await UserService.deleteUser(username);
        authContext.onLogout();
    }, [username, authContext]);

    useEffect(() => {
        if (deleteUser) {
            deleteUserAccount();
            setDeleteUser(false);
        }
    }, [deleteUser, deleteUserAccount])

    const updateUserData = (props) => {
        setToEditData(props);
    }
    const deleteAccount = () => {
        setDeleteUser(true);
    }
    return (
        <>
            <Helmet>
                <title>{t('settings_title')}</title>
            </Helmet>
            {userData === undefined && <Spinner/>}
            {userData !== undefined && <>
                <SettingsUserProfile name={userData.name} username={userData.username} image={userData.imageUrl}
                                     email={userData.email} isIncorrectPassword={passwordError}
                                     onSaveUserData={updateUserData}
                                     onDeleteAccount={deleteAccount}/>

                {successfulUpdate && <Navigate to={'/user/' + userData.username}/>}
                {deleteUser && <Navigate to='/'/>}
            </>
            }
        </>
    );
}
export default Settings;