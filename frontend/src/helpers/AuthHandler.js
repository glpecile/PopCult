import {useContext} from "react";
import AuthContext from "../store/AuthContext";
import {useNavigate} from "react-router-dom";

export function AuthHandler(){
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();
    if (authContext.isLoggedIn){
        return true;
    }else{
        navigate('/login');
    }
}