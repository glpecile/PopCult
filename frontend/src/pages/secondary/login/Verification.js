import {Navigate, useLocation} from "react-router-dom";
import {useCallback, useContext, useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import AuthContext from "../../../store/AuthContext";
import jwtDecode from "jwt-decode";
import useErrorStatus from "../../../hooks/useErrorStatus";

const Verification = () => {
    const [successfulVerification, setSuccessfulVerification] = useState(false);
    const query = new URLSearchParams(useLocation().search);
    const token = query.get('token');
    const authContext = useContext(AuthContext);
    const {setErrorStatusCode} = useErrorStatus();

    const verifyAccount = useCallback(async (token) => {
        try {
            const key = await UserService.verifyUser({token})
            setSuccessfulVerification(true);
            authContext.onLogin(key, jwtDecode(key).sub);
            sessionStorage.setItem("userAuthToken", JSON.stringify(key));
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }, [authContext, setErrorStatusCode]);

    useEffect(() => {
        if (token !== null && token.length !== 0) {
            verifyAccount(token);
        }
    }, [token, verifyAccount]);

    return <>
        {successfulVerification && <Navigate to='/'/>}
    </>;
}
export default Verification;