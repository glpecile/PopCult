import UserProfile from "../../../components/profile/UserProfile";
import UserTabs from "../../../components/profile/UserTabs";
import {useParams} from "react-router-dom";
import {useContext, useEffect, useRef, useState} from "react";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import AuthContext from "../../../store/AuthContext";
import UserContext from "../../../store/UserContext";


const Profile = () => {
    let {username} = useParams();
    const loggedUsername = useContext(AuthContext).username;
    const [userData, setUserData] = useState('');
    const mountedUser = useRef(true);
    const {t} = useTranslation();
    const userContext = useContext(UserContext);

    const getUser = userContext.getUser;


    useEffect(() => {
        mountedUser.current = true;
        getUser(mountedUser, username, setUserData);
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