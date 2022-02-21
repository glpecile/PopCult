import {useState} from "react";
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
    const [isLoggedIn, setLoggedIn] = useState(localStorage.hasOwnProperty("userAuthToken"));
    console.log("is logged in: " + isLoggedIn);
    const [authKey, setAuthKey] = useState(JSON.parse(localStorage.getItem("userAuthToken")));
    const [username, setUsername] = useState(() => {
        const token = JSON.parse(localStorage.getItem("userAuthToken"));
        try {
            return jwtDecode(token).sub;
        } catch (error) {
            console.log(error);
        }
    });

    const logoutHandler = () => {
        console.log("logging out");
        localStorage.removeItem("userAuthToken");
        setLoggedIn(false);
        setAuthKey('');
        setUsername('');
    }

    const loginHandler = (authKey, username) => {
        console.log("logging in");
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