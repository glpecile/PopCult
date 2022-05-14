import {AuthContextProvider} from "./AuthContext";
import {HelmetProvider} from "react-helmet-async";
import {GenresContextProvider} from "./GenresContext";
import {ErrorHandler} from "./ErrorStatusContext";
import {UserContextProvider} from "./UserContext";

const ContextProviderWrapper = (props) => {
    const helmetContext = {};

    return (
        <GenresContextProvider>
            <AuthContextProvider>
                <ErrorHandler>
                    <UserContextProvider>
                        <HelmetProvider context={helmetContext}>
                            {props.children}
                        </HelmetProvider>
                    </UserContextProvider>
                </ErrorHandler>
            </AuthContextProvider>
        </GenresContextProvider>
    );

}
export default ContextProviderWrapper;