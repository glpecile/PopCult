import {AuthContextProvider} from "./AuthContext";
import {HelmetProvider} from "react-helmet-async";
import {UserContextProvider} from "./UserContext";

const ContextProviderWrapper = (props) => {
    const helmetContext = {};

    return (
        <UserContextProvider>
            <AuthContextProvider>
                <HelmetProvider context={helmetContext}>
                    {props.children}
                </HelmetProvider>
            </AuthContextProvider>
        </UserContextProvider>);

}
export default ContextProviderWrapper;