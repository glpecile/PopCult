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

    const UserListsTab = () => {
        return (userListsActive ? (
            <button
                className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                {props.username}'s Lists
            </button>) : (<button onClick={() => {
            setUserListsActive(true)
            setUserFavMediaActive(false);
            setUserFavListsActive(false);
            setUserWatchedMediaActive(false);
            setUserToWatchMediaActive(false);
        }}
                                  className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
            {props.username}'s Lists
        </button>));
    }

    const UserFavMediaTab = () => {
        return (userFavMediaActive ? (
            <button
                className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                Favorite Media
            </button>) : (
            <button onClick={() => {
                setUserListsActive(false);
                setUserFavMediaActive(true);
                setUserFavListsActive(false);
                setUserWatchedMediaActive(false);
                setUserToWatchMediaActive(false);
            }}
                    className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                Favorite Media
            </button>));
    }

    const UserFavListsTab = () => {
        return (userFavListsActive ? (
            <button
                className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
                Favorite Lists
            </button>
        ) : (
            <button onClick={() => {
                setUserListsActive(false);
                setUserFavMediaActive(false);
                setUserFavListsActive(true);
                setUserWatchedMediaActive(false);
                setUserToWatchMediaActive(false);
            }}
                    className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                Favorite Lists
            </button>
        ));
    }

    const UserWatchedMediaTab = () => {
        return (userWatchedMediaActive ? (<button
            className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
            Watched Media </button>) : (
            <button onClick={() => {
                setUserListsActive(false);
                setUserFavMediaActive(false);
                setUserFavListsActive(false);
                setUserWatchedMediaActive(true);
                setUserToWatchMediaActive(false);
            }} className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                Watched Media </button>));
    }

    const UserToWatchMediaTab = () => {
        return (userToWatchMediaActive ? (<button
            className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none text-purple-500 border-b-2 font-medium border-purple-500">
            To Watch Media </button>) : (
            <button onClick={() => {
                setUserListsActive(false);
                setUserFavMediaActive(false);
                setUserFavListsActive(false);
                setUserWatchedMediaActive(false);
                setUserToWatchMediaActive(true);
            }} className="text-gray-600 py-2 px-6 block hover:text-purple-500 focus:outline-none">
                To Watch Media </button>));
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