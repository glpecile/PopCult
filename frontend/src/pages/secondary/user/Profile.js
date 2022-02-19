import UserProfile from "../../../components/profile/UserProfile";
import UserTabs from "../../../components/profile/UserTabs";
import {useParams} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import userService from "../../../services/UserService";
import {Helmet} from "react-helmet";
import {useTranslation} from "react-i18next";


const Profile = () => {
    let {username} = useParams();
    const user = JSON.parse(localStorage.getItem("user"));
    const [show, setShow] = useState('');
    const mountedUser = useRef(true);
    const {t} = useTranslation();

    useEffect(() => {
        mountedUser.current = true;
        userService.getUser(username)
            .then(items => {
                if (mountedUser.current) {
                    setShow(items);
                }
            })
        return () => mountedUser.current = false;
    }, [username]);

    return (
        <>
            <Helmet>
                <title>{t('profile_title')}</title>
            </Helmet>
            <UserProfile id={show.username} name={show.name} username={show.username}
                         image={''} isCurrentUser={username.localeCompare(user.username) === 0}/>
            <UserTabs username={show.username} id={show.username}/>
        </>);
}

export default Profile;