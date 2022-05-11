import {useContext, useEffect} from "react";
import AuthContext from "../../store/AuthContext";
import {useLocation, useNavigate} from "react-router-dom";

const LoggedGate = ({children}) => {
    const isLogged = useContext(AuthContext).isLoggedIn;
    const location = useLocation()
    const navigate = useNavigate();

    useEffect(() => {
        if (!isLogged) {
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            })
        }
    }, [isLogged, navigate, location.pathname]);

    return <>{children}</>
}
export default LoggedGate;