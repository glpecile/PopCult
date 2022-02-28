import RegisterCard from "../../../components/login/RegisterCard";
import RegisterForm from "../../../components/login/RegisterForm";
import {useCallback, useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import UserService from "../../../services/UserService";

const Register = () => {
    const [registerError, setRegisterError] = useState(false);
    const [userToRegister, setUserToRegister] = useState(undefined);
    const [usernameExists, setUsernameExists] = useState(false);
    const [emailExists, setEmailExists] = useState(false);
    const navigate = useNavigate();

    const mountedUser = useRef(true);

    const registerUser = useCallback(async (email, username, password, name) => {
        let regError = false;
        if (mountedUser.current) {
            try {
                await UserService.createUser({email, username, password, name})
                setRegisterError(false);
            } catch (error) {
                regError = true;
                if (error.response.data.message.localeCompare("Username already exists") === 0) {
                    setUsernameExists(true);
                    setTimeout(() => {
                        setUsernameExists(false);
                    }, 5000)
                }
                if (error.response.data.message.localeCompare("Email already exists") === 0) {
                    setEmailExists(true);
                    setTimeout(() => {
                        setEmailExists(false);
                    }, 5000)
                }
                setRegisterError(true);
                console.log(error.response.data);
            }
            if (!regError) navigate("/register/success")
        }
    }, [navigate]);

    useEffect(() => {
        mountedUser.current = true;
        if (userToRegister !== undefined)
            registerUser(userToRegister.email, userToRegister.username, userToRegister.password, userToRegister.name);
        return () => {
            mountedUser.current = false;
        }
    }, [userToRegister, registerUser]);

    const SendRegForm = (user) => {
        console.log(user);
        setUserToRegister(user);
    }
    return (
        <RegisterCard>
            <RegisterForm onSuccessfulRegister={SendRegForm} registrationError={registerError}
                          usernameExists={usernameExists} emailExists={emailExists}/>
        </RegisterCard>
    );
}
export default Register;