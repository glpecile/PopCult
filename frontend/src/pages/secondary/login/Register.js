import RegisterCard from "../../../components/login/RegisterCard";
import RegisterForm from "../../../components/login/RegisterForm";
import {useState} from "react";
import {Navigate} from "react-router-dom";

const Register = () => {
    const [registerState, setRegisterState] = useState(false);
    const SendRegForm = () => {
        setRegisterState(true);
    }
    return(
        <RegisterCard>
            <RegisterForm onSuccessfulRegister={SendRegForm}/>
            {registerState && <Navigate to='/login'/>}
        </RegisterCard>
    );
}
export default Register;