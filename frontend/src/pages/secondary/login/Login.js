import LoginCard from "../../../components/login/LoginCard";
import {Navigate} from "react-router-dom";
import {useEffect, useState, useContext, useRef, useCallback} from "react";
import UserService from "../../../services/UserService";
import {useTranslation} from "react-i18next";
import AuthContext from "../../../store/AuthContext";
import jwtDecode from "jwt-decode";

function Login() {
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredRememberMe, setEnteredRememberMe] = useState(false);
    const [enteredUsernameError, setUsernameError] = useState(false);
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [errorMessageDisplay, setErrorMessageDisplay] = useState(false);
    const mountedUser = useRef(true);

    const [t] = useTranslation();

    const [logInState, setLogInState] = useState(localStorage.hasOwnProperty("userAuthToken") || sessionStorage.hasOwnProperty("userAuthToken"));
    const [loginCredentials, setCredentials] = useState({username: '', password: '', rememberMe: false});
    const authContext = useContext(AuthContext);

    const login = useCallback(async (username, password, rememberMe) => {

            try {
                const key = await UserService.login({username, password})
                if (mountedUser.current) {
                    setErrorMessageDisplay(false);
                    setLogInState(true);
                    authContext.onLogin(key, username);
                    console.log(jwtDecode(key));
                    rememberMe === true ? localStorage.setItem("userAuthToken", JSON.stringify(key)) : sessionStorage.setItem("userAuthToken", JSON.stringify(key));
                }
            } catch (error) {
                console.log(error.response);
                setErrorMessage();
            }
        },
        [authContext]);

    useEffect(() => {
        mountedUser.current = true;
        if (loginCredentials.username.localeCompare("") !== 0 && loginCredentials.password.localeCompare("") !== 0) {
            const username = loginCredentials.username;
            const password = loginCredentials.password;
            const rememberMe = loginCredentials.rememberMe;
            login(username, password, rememberMe);
        }
        return () => {
            mountedUser.current = false;
        }
    }, [loginCredentials, authContext, login]);


    const UsernameChangeHandler = (event) => {
        setEnteredUsername(event.target.value);
        event.target.validity.valid ? setUsernameError(false) : setUsernameError(true);


    };
    const PasswordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
        event.target.validity.valid ? setPasswordError(false) : setPasswordError(true);

    };
    const RememberMeChangeHandler = (event) => {
        setEnteredRememberMe(event.target.value);

    };

    const setErrorMessage = () => {
        setErrorMessageDisplay(true);
        setTimeout(() => {
            setErrorMessageDisplay(false);
        }, 5000);
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if (!(enteredPasswordError || enteredUsernameError) && enteredUsername.length !== 0 && enteredPassword.length !== 0) {
            setCredentials({username: enteredUsername, password: enteredPassword, rememberMe: enteredRememberMe});
        } else {
            setErrorMessage();
        }
    };

    return <LoginCard>
        {logInState && <Navigate to='/'/>}
        <form className="my-1.5" onSubmit={submitHandler} noValidate={true}>
            <div className="flex flex-col">
                <label className="py-2 text-semibold w-full">
                    {t('login_username')}
                </label>
                <input className="w-full rounded active:none shadow-sm accent-purple-400" type="text"
                       value={enteredUsername}
                       pattern="[a-zA-Z0-9]+" minLength={1} maxLength={100}
                       onChange={UsernameChangeHandler}/>

                <label className="py-2 text-semibold w-full">
                    {t('login_password')}
                </label>
                <input className="w-full rounded active:none shadow-sm" type="password" value={enteredPassword}
                       minLength={8} maxLength={100}
                       onChange={PasswordChangeHandler}/>

                <div className="flex py-2.5 justify-start">
                    <input className="shadow-sm mt-1.5 accent-purple-400" type="checkbox"
                           defaultChecked={enteredRememberMe}
                           onChange={RememberMeChangeHandler}/>
                    <label className="text-semibold pl-1.5">
                        {t('login_remember_me')}
                    </label>
                </div>
                {errorMessageDisplay && <div className="py-1 text-semibold text-danger w-full">
                    {t('login_error')}
                </div>}
                <button className="btn btn-secondary my-2 w-full hover:shadow-indigo-50" type="submit">
                    {t('login_button')}
                </button>
                <button className="text-purple-800 hover:text-indigo-900 text-sm uppercase">
                    {t('login_recovery')}
                </button>
            </div>
        </form>
    </LoginCard>

}

export default Login;