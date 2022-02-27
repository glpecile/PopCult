import {Navigate, useLocation} from "react-router-dom";
import {useCallback, useContext, useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import AuthContext from "../../../store/AuthContext";
import jwtDecode from "jwt-decode";

const Verification = () => {
    const [successfulVerification, setSuccessfulVerification] = useState(false);
    const [expiredToken, setExpiredToken] = useState(false);
    const query = new URLSearchParams(useLocation().search);
    const token = query.get('token');
    const authContext = useContext(AuthContext);

    const verifyAccount = useCallback(async (token) => {
        try {
            const key = await UserService.verifyUser({token})
            setExpiredToken(false);
            setSuccessfulVerification(true);
            authContext.onLogin(key, jwtDecode(key).sub);
            sessionStorage.setItem("userAuthToken", JSON.stringify(key));
        } catch (error) {
            console.log(error);
        }
    }, [authContext]);

    useEffect(() => {
        console.log("token "+token);
        if (token !== null && token.length !== 0) {
            verifyAccount(token);
        }
    }, [token, verifyAccount]);

    return <>
        {successfulVerification && <Navigate to='/'/>}
    </>;
}
export default Verification;