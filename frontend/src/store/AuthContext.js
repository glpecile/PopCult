import {useState} from "react";
import React from "react";
import jwtDecode from "jwt-decode";
import api from "../api/api";
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
    const isInLocalStorage = localStorage.hasOwnProperty("userAuthToken");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || sessionStorage.hasOwnProperty("userAuthToken"));
    const token = isInLocalStorage ? JSON.parse(localStorage.getItem("userAuthToken")) : JSON.parse(sessionStorage.getItem("userAuthToken"))
    const [authKey, setAuthKey] = useState(token);
    api.defaults.headers.common['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem("userAuthToken")) || JSON.parse(sessionStorage.getItem("userAuthToken"))|| ''}`;

    const [username, setUsername] = useState(() => {
        try {
            return jwtDecode(token).sub;
        } catch (error) {
            if (isLoggedIn) {
                console.log(error);
            }
        }
    });


    const logoutHandler = () => {
        localStorage.removeItem("userAuthToken");
        sessionStorage.removeItem("userAuthToken");
        setLoggedIn(false);
        setAuthKey('');
        setUsername('');
    }


    const loginHandler = (authKey, username) => {
        setAuthKey(authKey);
        setUsername(username);
        setLoggedIn(true);
        api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
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