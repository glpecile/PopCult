import React, {useCallback} from "react";
import userService from "../services/UserService";

const UserContext = React.createContext({
    getUser: () => {}
});

export const UserContextProvider = (props) => {
    const getUser = useCallback(async (isMounted, username, setUserData) => {
        console.log("get user call")
        try {
            if (isMounted.current) {
                const user = await userService.getUser(username);
                setUserData(user);
            }
        } catch (error) {
            console.log(error);
        }
    }, []);


    return <UserContext.Provider value={{
        getUser: getUser
    }}>{props.children}</UserContext.Provider>
}
export default UserContext;