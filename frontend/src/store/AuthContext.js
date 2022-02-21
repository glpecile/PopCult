import {useState} from "react";
import React from "react";
import jwtDecode from "jwt-decode";

const AuthContext = React.createContext({
    isLoggedIn: false,
    onLogout: () => {},
    onLogin: (authKey, username) => {},
    authKey: '',
    username: ''
});

export const AuthContextProvider = (props) => {
    const [isLoggedIn, setLoggedIn] = useState(localStorage.hasOwnProperty("userAuthToken"));
    const token = isLoggedIn ? JSON.parse(localStorage.getItem("userAuthToken")) : '';
    const [authKey, setAuthKey] = useState(token);
    const [username, setUsername] = useState(jwtDecode(token).sub);

    const logoutHandler = () => {
        setLoggedIn(false);
        setAuthKey('');
        setUsername('');
    }

    const loginHandler = (authKey, username) => {
        setAuthKey(authKey);
        setUsername(username);
        setLoggedIn(true);
        console.log(username);
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