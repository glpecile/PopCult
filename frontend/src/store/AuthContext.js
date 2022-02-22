import {useContext, useEffect, useState} from "react";
import React from "react";
import jwtDecode from "jwt-decode";
import UserContext from "./UserContext";

const AuthContext = React.createContext({
    isLoggedIn: false,
    onLogout: () => {
    },
    onLogin: (authKey, username) => {
    },
    authKey: '',
    username: ''
});

export const AuthContextProvider = (props) => {
    const [isLoggedIn, setLoggedIn] = useState(localStorage.hasOwnProperty("userAuthToken"));
    const setCurrentUser = useContext(UserContext).setCurrentUser;
    const [authKey, setAuthKey] = useState(JSON.parse(localStorage.getItem("userAuthToken")));
    const [username, setUsername] = useState(() => {
        const token = JSON.parse(localStorage.getItem("userAuthToken"));
        try {
            const username = jwtDecode(token).sub
            setCurrentUser(username);
            return username;
        } catch (error) {
            console.log(error);
        }
    });

    const logoutHandler = () => {
        localStorage.removeItem("userAuthToken");
        setLoggedIn(false);
        setAuthKey('');
        setUsername('');
    }

    useEffect(() => {
        let mountedUser = true;
        if (mountedUser)
        setCurrentUser(username);
        return () => {
            mountedUser = false
        };
    }, [username, setCurrentUser]);

    const loginHandler = (authKey, username) => {
        setAuthKey(authKey);
        setUsername(username);
        setLoggedIn(true);
    }

    return <AuthContext.Provider
        value={{
            isLoggedIn: isLoggedIn,
            onLogout: logoutHandler,
            onLogin: loginHandler,
            authKey: authKey,
            username: username
        }}>{props.children}</AuthContext.Provider>
}

export default AuthContext;