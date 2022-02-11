import {useState} from "react";
import UserLists from "./tabs/UserLists";
import UserWatchedMedia from "./tabs/UserWatchedMedia";
import UserFavoriteMedia from "./tabs/UserFavoriteMedia";
import UserFavoriteLists from "./tabs/UserFavoriteLists";
import UserToWatchMedia from "./tabs/UserToWatchMedia";


const UserTabs = (props) => {
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
                className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                {title}
            </button>) : (
            <button onClick={() => {
                setTabsActiveFalse();
                setTabActiveTrue(true);
            }} className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                {title}
            </button>));
    }

    const UserListsTab = () => {
        const title = props.username + "'s Lists"
        return toReturnValue(userListsActive, title, setUserListsActive);
    }

    const UserFavMediaTab = () => {
        const title = "Favorite Media";
        return toReturnValue(userFavMediaActive, title, setUserFavMediaActive);
    }

    const UserFavListsTab = () => {
        const title = "Favorite Lists";
        return toReturnValue(userFavListsActive, title, setUserFavListsActive);
    }

    const UserWatchedMediaTab = () => {
        const title = "Watched Media";
        return toReturnValue(userWatchedMediaActive, title, setUserWatchedMediaActive);
    }

    const UserToWatchMediaTab = () => {
        const title = "To Watch Media";
        return toReturnValue(userToWatchMediaActive, title, setUserToWatchMediaActive);
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