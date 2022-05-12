import {AuthContextProvider} from "./AuthContext";
import {HelmetProvider} from "react-helmet-async";
import {GenresContextProvider} from "./GenresContext";
import {ErrorHandler} from "./ErrorStatusContext";

const ContextProviderWrapper = (props) => {
    const helmetContext = {};

    return (
        <GenresContextProvider>
            <AuthContextProvider>
                <ErrorHandler>
                    <HelmetProvider context={helmetContext}>
                        {props.children}
                    </HelmetProvider>
                </ErrorHandler>
            </AuthContextProvider>
        </GenresContextProvider>
    );

}
export default ContextProviderWrapper;