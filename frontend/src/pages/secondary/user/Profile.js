import UserProfile from "../../../components/profile/UserProfile";
import UserTabs from "../../../components/profile/UserTabs";
import {useParams} from "react-router-dom";
import {useContext, useEffect, useRef, useState} from "react";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import AuthContext from "../../../store/AuthContext";
import UserService from "../../../services/UserService";
import Spinner from "../../../components/animation/Spinner";
import useErrorStatus from "../../../hooks/useErrorStatus";


const Profile = () => {
    let {username} = useParams();
    const loggedUsername = useContext(AuthContext).username;
    const [isCurrUser, setIsCurrUser] = useState(username.localeCompare(loggedUsername) === 0);
    const [userData, setUserData] = useState(undefined);
    const mountedUser = useRef(true);
    const {t} = useTranslation();
    const {setErrorStatusCode} = useErrorStatus();


    useEffect(() => {
        setIsCurrUser(username.localeCompare(loggedUsername) === 0);
    }, [username, loggedUsername]);


    useEffect(() => {
        mountedUser.current = true;

        async function getUserByUsername() {
            if (username && mountedUser.current) {
                try {
                    const user = await UserService.getUserByUsername(username);
                    setUserData(user);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }
        };

        getUserByUsername();
        return () => {
            mountedUser.current = false;
        }
    }, [username, setErrorStatusCode])

    return (
        <>
            <Helmet>
                <title>{t('profile_title')}</title>
            </Helmet>
            {userData === undefined && <Spinner/>}
            {
                userData !== undefined &&
                <>
                    <UserProfile id={userData.username}
                                 name={userData.name}
                                 username={userData.username}
                                 image={userData.imageUrl}
                                 isCurrentUser={isCurrUser}/>
                    <UserTabs username={userData.username} id={userData.username} currentUser={isCurrUser} userData={userData}/>
                </>
            }
        </>
    );
}

export default Profile;