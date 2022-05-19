import {useContext, useEffect, useState} from "react";
import React from "react";
import useErrorStatus from "../hooks/useErrorStatus";
import AuthContext from "./AuthContext";
import userService from "../services/UserService";

const UserContext = React.createContext({});

export const UserContextProvider = (props) => {
    const [user, setUser] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();
    const authContext = useContext(AuthContext);

    useEffect(() => {
        async function getUser() {
            try {
                const data = await userService.getUserByUsername(authContext.username);
                setUser(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (authContext.isLoggedIn)
            getUser();
    }, [setErrorStatusCode, authContext.isLoggedIn]);

    return <UserContext.Provider
        value={{user: user}}>{props.children}</UserContext.Provider>
}

export default UserContext;