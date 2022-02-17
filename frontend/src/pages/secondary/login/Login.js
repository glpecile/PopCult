import LoginWrapper from "../../../components/login/LoginWrapper";
import LoginForm from "../../../components/login/LoginForm";
import {Navigate} from "react-router-dom";
import {useEffect, useState} from "react";
import UserService from "../../../services/UserService";

function Login() {
    const [logInState, setLogInState] = useState(false);
    const [loginCredentials, setCredentials] = useState({username: '', password: ''});
    useEffect(() => {
        let mountedUser = true;
        mountedUser = true;
        if (loginCredentials.username.localeCompare("") !== 0 && loginCredentials.password.localeCompare("") !== 0) {
            const username = loginCredentials.username;
            const password = loginCredentials.password;
            UserService.login({username, password})
                .then(key => {
                    if (mountedUser) {
                        console.log(key);
                        //save key local storage
                        setLogInState(true);
                    }
                })
        }
        return () => {
            mountedUser = false;
        }
    }, [loginCredentials]);


    const LogIn = (username, password) => {
        setCredentials({username: username, password: password});
    }
    return <LoginWrapper>
        <LoginForm onSuccessfullLogIn={LogIn}/>
        {logInState && <Navigate to='/'/>}
    </LoginWrapper>

}

export default Login;