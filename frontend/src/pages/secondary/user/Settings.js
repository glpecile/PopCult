import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import UserService from "../../../services/UserService";
import AuthContext from "../../../store/AuthContext";
import * as PropTypes from "prop-types";
import Spinner from "../../../components/animation/Spinner";
import {useNavigate} from 'react-router-dom'
import NoButtonDialog from "../../../components/modal/NoButtonDialog";


function Suspense(props) {
    return null;
}

Suspense.propTypes = {
    fallback: PropTypes.any,
    children: PropTypes.node
};

function Navigate(props) {
    return null;
}

Navigate.propTypes = {to: PropTypes.string};
const Settings = () => {
    const {t} = useTranslation();
    const mountedUser = useRef(true);
    const mountedEditUser = useRef(true);
    const [toEditData, setToEditData] = useState(undefined);
    const [userData, setUserData] = useState(undefined);
    const [passwordError, setPasswordError] = useState(false);
    const [deleteUser, setDeleteUser] = useState(false);
    const [successModal, setSuccessModal] = useState(false);
    const [errorModal, setErrorModal] = useState(false);
    const username = useRef(useContext(AuthContext)).current.username;
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();


    const getUser = useCallback(async () => {
        if (username) {
            try {
                const user = await UserService.getUser(username);
                setUserData(user);
            } catch (error) {
                console.log(error);
               navigate(`/user/${username}`);
            }
        }
    }, [username, navigate]);

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
            if (!wrongPass) navigate(`/user/${username}`);
        }
    }, [toEditData, username, userData, navigate]);

    useEffect(() => {
        mountedEditUser.current = true;
        editUser();
        return () => {
            mountedEditUser.current = false;
        }
    }, [username, editUser]);

    const deleteUserAccount = useCallback(async () => {
        try {
            await UserService.deleteUser(username);
            authContext.onLogout();
            setSuccessModal(true);
            setTimeout(() => {
                setSuccessModal(false);
                setErrorModal(false);
                navigate('/');
            }, 5000);
        } catch (error) {
            setErrorModal(true);
            console.log(error.response);
            setTimeout(() => {
                setSuccessModal(false);
                setErrorModal(false);
                // navigate(`/user/${username}`);
            }, 5000);
        }
    }, [username, authContext, navigate]);

    useEffect(() => {
        if (deleteUser) {
            deleteUserAccount();
            setDeleteUser(false);
        }
    }, [deleteUser, deleteUserAccount, navigate])

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
                {successModal && <NoButtonDialog
                    buttonClassName="btn bg-gray-300 shadow-md group hover:bg-green-400 hover:shadow-green-300 text-gray-700 font-semibold hover:text-white my-2"
                    buttonIcon={<i className="fas fa-user-alt-slash group-hover:text-white mr-2"/>}
                    buttonText={t('profile_settings_deleteUser')}
                    title={t('modal_success')}
                    body={t('modal_user_delete_success')}
                    isOpened={true}/>}
                {errorModal && <NoButtonDialog
                    buttonClassName="btn my-2 bg-gray-300 shadow-md group hover:bg-red-400 hover:shadow-red-400 text-gray-700 font-semibold hover:text-white"
                    buttonIcon={<i className="fas fa-user-alt-slash group-hover:text-white mr-2"/>}
                    buttonText={t('profile_settings_deleteUser')}
                    title={t('modal_error')}
                    body={t('modal_delete_user_error')}
                    isOpened={true}/>}
            </>
            }
        </>
    );
}
export default Settings;