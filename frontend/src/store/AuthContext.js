import {useState} from "react";
import React from "react";

const AuthContext = React.createContext({
    isLoggedIn: false,
    onLogout: () => {},
    onLogin: (authKey, username) => {},
    authKey: '',
    username: ''
});

export const AuthContextProvider = (props) => {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [authKey, setAuthKey] = useState('');
    const [username, setUsername] = useState('');

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
    return <AuthContext.Provider value={{
        isLoggedIn: isLoggedIn,
        onLogout: logoutHandler,
        onLogin: loginHandler,
        authKey: authKey,
        username: username
    }}>{props.children}</AuthContext.Provider>
}

export default AuthContext;