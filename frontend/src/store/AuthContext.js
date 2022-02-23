import {useContext, useEffect, useRef, useState} from "react";
import React from "react";
import jwtDecode from "jwt-decode";

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
    const mountedUser = useRef(true);
    const isInLocalStorage = localStorage.hasOwnProperty("userAuthToken");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || sessionStorage.hasOwnProperty("userAuthToken"));
    const token = isInLocalStorage ? JSON.parse(localStorage.getItem("userAuthToken")) : JSON.parse(sessionStorage.getItem("userAuthToken"))
    const [authKey, setAuthKey] = useState(token);

    const [username, setUsername] = useState(() => {
        try {
            return jwtDecode(token).sub;
        } catch (error) {
            console.log(error);
        }
    });

    const logoutHandler = () => {
        localStorage.removeItem("userAuthToken");
        sessionStorage.removeItem("userAuthToken");
        setLoggedIn(false);
        setAuthKey('');
        setUsername('');
    }

    useEffect(() => {
        mountedUser.current= true;
        if (mountedUser.current && username !== undefined)
        return () => {
            mountedUser.current = false
        };
    }, [username]);

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