import UserProfile from "../../../components/profile/UserProfile";
import UserTabs from "../../../components/profile/UserTabs";
import {useParams} from "react-router-dom";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
import userService from "../../../services/UserService";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import AuthContext from "../../../store/AuthContext";


const Profile = () => {
    let {username} = useParams();
    const loggedUsername = useContext(AuthContext);
    const [userData, setUserData] = useState('');
    const mountedUser = useRef(true);
    const {t} = useTranslation();

    const getUser = useCallback(async () => {
            try {
                if (mountedUser.current) {
                    const user = await userService.getUser(username);
                    setUserData(user);
                }
            } catch (error) {
                console.log(error);
            }
        },
        [username]);

    useEffect(() => {
        mountedUser.current = true;
        getUser();
        return () => {
            mountedUser.current = false
        };
    }, [username, getUser]);

    return (
        <>
            <Helmet>
                <title>{t('profile_title')}</title>
            </Helmet>
            <UserProfile id={userData.username} name={userData.name} username={userData.username}
                         image={''} isCurrentUser={username.localeCompare(loggedUsername) === 0}/>
            <UserTabs username={userData.username} id={userData.username}/>
        </>);
}

export default Profile;