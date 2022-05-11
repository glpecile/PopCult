import {useContext, useEffect} from "react";
import AuthContext from "../../store/AuthContext";
import Login from "../../pages/secondary/login/Login";
import {useLocation, useNavigate} from "react-router-dom";

const LoggedGate = ({children}) => {
    const isLogged = useContext(AuthContext).isLoggedIn;
    const location = useLocation()
    const navigate = useNavigate();

    useEffect(() => {
        if (!isLogged) {
            navigate( '/login', {url: location.pathname})
        }
    }, []);
    return <>{children}</>
}
export default LoggedGate;