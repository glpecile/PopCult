import RegisterWrapper from "../../../components/login/RegisterWrapper";
import RegisterForm from "../../../components/login/RegisterForm";
import {useState} from "react";
import {Navigate} from "react-router-dom";

const Register = () => {
    const [registerState, setRegisterState] = useState(false);
    const SendRegForm = () => {
        setRegisterState(true);
    }
    return(
        <RegisterWrapper>
            <RegisterForm onSuccessfulRegister={SendRegForm}/>
            {registerState && <Navigate to='/login'/>}
        </RegisterWrapper>
    );
}
export default Register;