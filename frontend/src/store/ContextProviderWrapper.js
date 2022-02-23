import {AuthContextProvider} from "./AuthContext";
import {HelmetProvider} from "react-helmet-async";

const ContextProviderWrapper = (props) => {
    const helmetContext = {};

    return (
            <AuthContextProvider>
                <HelmetProvider context={helmetContext}>
                    {props.children}
                </HelmetProvider>
            </AuthContextProvider>);

}
export default ContextProviderWrapper;