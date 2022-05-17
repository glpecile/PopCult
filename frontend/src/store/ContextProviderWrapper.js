import {AuthContextProvider} from "./AuthContext";
import {GenresContextProvider} from "./GenresContext";
import {ErrorHandler} from "./ErrorStatusContext";
import {UserContextProvider} from "./UserContext";

const ContextProviderWrapper = (props) => {

    return (
        <GenresContextProvider>
            <AuthContextProvider>
                <ErrorHandler>
                    <UserContextProvider>
                        {props.children}
                    </UserContextProvider>
                </ErrorHandler>
            </AuthContextProvider>
        </GenresContextProvider>
    );

}
export default ContextProviderWrapper;