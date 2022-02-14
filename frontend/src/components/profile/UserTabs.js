import {useState} from "react";
import UserLists from "./tabs/UserLists";
import UserWatchedMedia from "./tabs/UserWatchedMedia";
import UserFavoriteMedia from "./tabs/UserFavoriteMedia";
import UserFavoriteLists from "./tabs/UserFavoriteLists";
import UserToWatchMedia from "./tabs/UserToWatchMedia";
import {Trans, useTranslation} from "react-i18next";


const UserTabs = (props) => {
    const {t} = useTranslation();

    const [userListsActive, setUserListsActive] = useState(true);
    const [userFavMediaActive, setUserFavMediaActive] = useState(false);
    const [userFavListsActive, setUserFavListsActive] = useState(false);
    const [userWatchedMediaActive, setUserWatchedMediaActive] = useState(false);
    const [userToWatchMediaActive, setUserToWatchMediaActive] = useState(false);

    const setTabsActiveFalse = () => {
        setUserListsActive(false);
        setUserFavMediaActive(false);
        setUserFavListsActive(false);
        setUserWatchedMediaActive(false);
        setUserToWatchMediaActive(false);
    }
    const toReturnValue = (bool, title, setTabActiveTrue) => {
        return (bool ? (
            <button
                className="py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                {title}
            </button>) : (
            <button onClick={() => {
                setTabsActiveFalse();
                setTabActiveTrue(true);
            }} className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                {title}
            </button>));
    }

    const Inter = () => {
        console.log(props.username);
        const username = props.username;
        return (
            <Trans i18nKey="profile_tabs_main">
                {{username}}
            </Trans>)
    }
    const UserListsTab = () => {
        return toReturnValue(userListsActive, <Inter/>, setUserListsActive);
    }

    const UserFavMediaTab = () => {
        return toReturnValue(userFavMediaActive, t('profile_tabs_favMedia'), setUserFavMediaActive);
    }

    const UserFavListsTab = () => {
        return toReturnValue(userFavListsActive, t('profile_tabs_favLists'), setUserFavListsActive);
    }

    const UserWatchedMediaTab = () => {
        return toReturnValue(userWatchedMediaActive, t('profile_tabs_watchedMedia'), setUserWatchedMediaActive);
    }

    const UserToWatchMediaTab = () => {
        return toReturnValue(userToWatchMediaActive, t('profile_tabs_watchedMedia'), setUserToWatchMediaActive);
    }
    return (<>
            <div className="flex justify-center items-center bg-transparent py-4">
                <nav className="flex flex-col sm:flex-row">
                    <UserListsTab/>
                    <UserFavMediaTab/>
                    <UserFavListsTab/>
                    <UserWatchedMediaTab/>
                    <UserToWatchMediaTab/>

                </nav>
            </div>
            <div className="row">
                {userListsActive && <UserLists/>}
                {userFavMediaActive && <UserFavoriteMedia/>}
                {userFavListsActive && <UserFavoriteLists/>}
                {userWatchedMediaActive && <UserWatchedMedia/>}
                {userToWatchMediaActive && <UserToWatchMedia/>}
            </div>
        </>
    );
}
export default UserTabs;