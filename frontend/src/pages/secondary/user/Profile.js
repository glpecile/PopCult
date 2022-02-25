import UserProfile from "../../../components/profile/UserProfile";
import UserTabs from "../../../components/profile/UserTabs";
import {useParams} from "react-router-dom";
import {useCallback, useContext, useEffect, useRef, useState} from "react";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import AuthContext from "../../../store/AuthContext";
import Error404 from "../errors/Error404";
import UserService from "../../../services/UserService";


const Profile = () => {
    let {username} = useParams();
    const loggedUsername = useContext(AuthContext).username;
    const [isCurrUser, setIsCurrUser] = useState(username.localeCompare(loggedUsername) === 0);
    const [userData, setUserData] = useState('');
    const [userError, setUserError] = useState(false);
    const mountedUser = useRef(true);
    const {t} = useTranslation();

    useEffect(() => {
        setIsCurrUser(username.localeCompare(loggedUsername) === 0);
        setUserError(false);
    }, [username, loggedUsername]);

    const getUser = useCallback(async () => {
        if (username && mountedUser.current) {
            try {
                const user = await UserService.getUser(username);
                setUserData(user);
            } catch (error) {
                console.log(error);
                setUserError(true);
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

    return (
        <>{!userError && <>
            <Helmet>
                <title>{t('profile_title')}</title>
            </Helmet>
            <UserProfile id={userData.username} name={userData.name} username={userData.username}
                         image={userData.imageUrl} isCurrentUser={isCurrUser}/>
            <UserTabs username={userData.username} id={userData.username}/> </>}
            {userError && <Error404/>}
        </>);
}

export default Profile;