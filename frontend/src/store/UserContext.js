import React, {useCallback, useRef, useState} from "react";
import userService from "../services/UserService";

const UserContext = React.createContext({
    getUser: () => {
    },
    setCurrentUser: (username) => {
    },
    editCurrentUser: () => {
    },
    getCurrentUser: '',
});

export const UserContextProvider = (props) => {
    const mountedUser = useRef(true);
    const [currUserData, setCurrUserData] = useState('');

    const getUser = useCallback(async (isMounted, username, setUserData) => {
        try {
            if (isMounted.current) {
                const user = await userService.getUser(username);
                setUserData(user);
            }
        } catch (error) {
            console.log(error);
        }
    }, []);

    const setUser = useCallback((username) => {
        mountedUser.current = true;
        getUser(mountedUser, username, setCurrUserData);
        return () => {
            mountedUser.current = false
        };
    }, [getUser]);

    const editUser = useCallback(async (isMounted, name) => {
        try {
            if (isMounted.current) {
                await userService.editUser({username: currUserData.username, name: name});
                setUser(currUserData.username);
            }
        } catch (error) {
            console.log(error);
        }
    }, [currUserData.username, setUser]);


    return <UserContext.Provider value={{
        getUser: getUser,
        setCurrentUser: setUser,
        editCurrentUser: editUser,
        getCurrentUser: currUserData
    }}>{props.children}</UserContext.Provider>
}
export default UserContext;