import LoginWrapper from "../../../components/login/LoginWrapper";
import LoginForm from "../../../components/login/LoginForm";
import {Navigate} from "react-router-dom";
import {useState} from "react";

function Login() {
    const [logInState, setLogInState] = useState(false);
    const logIn = (props) => {
        setLogInState(true);
    }
    return <LoginWrapper>
        <LoginForm onSuccessfullLogIn={logIn}/>
        {logInState && <Navigate to='/'/>}
    </LoginWrapper>

}

export default Login;