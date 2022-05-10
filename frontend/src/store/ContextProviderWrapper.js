import {AuthContextProvider} from "./AuthContext";
import {HelmetProvider} from "react-helmet-async";
import {GenresContextProvider} from "./GenresContext";

const ContextProviderWrapper = (props) => {
    const helmetContext = {};

    return (
        <AuthContextProvider>
            <GenresContextProvider>
                <HelmetProvider context={helmetContext}>
                    {props.children}
                </HelmetProvider>
            </GenresContextProvider>
        </AuthContextProvider>);

}
export default ContextProviderWrapper;