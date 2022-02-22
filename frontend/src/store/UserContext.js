import React, {useCallback, useState} from "react";
import UserService from "../services/UserService";
import userService from "../services/UserService";

const UserContext = React.createContext({
    getUser: (username, isCurrentUser, setUserData, setError) => {
    },
    setCurrentUser: (username) => {
    },
    editCurrentUser: () => {
    },
    currentUser: '',
});

export const UserContextProvider = (props) => {
    const [userData, setUserData] = useState('');


    const getUser = useCallback(async (username, isCurrentUser, setUserData, setError) => {
        if (isCurrentUser && userData) {
            setUserData(userData);
        } else {
            try {
                const user = await UserService.getUser(username);
                setUserData(user);
            } catch (error) {
                setError(true);
            }
        }
    }, [userData]);

    const setCurrentUser = useCallback(async (username) => {
        const user = await UserService.getUser(username);
        setUserData(user);
        console.log(username);
    }, []);

    const editCurrentUser = useCallback((name) => {
        console.log("editing");
        try {
            userService.editUser({username: userData.username, name: name});
            setUserData((prevState => {
                return {...prevState, name: name};
            }));
        } catch (error) {
            console.log(error);
        }
    }, [userData.username]);

    return <UserContext.Provider value={{
        getUser,
        setCurrentUser,
        editCurrentUser,
        currentUser: userData
    }}>{props.children}</UserContext.Provider>
}
export default UserContext;