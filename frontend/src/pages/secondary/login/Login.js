import LoginCard from "../../../components/login/LoginCard";
import {Navigate} from "react-router-dom";
import {useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import {useTranslation} from "react-i18next";

function Login() {
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredRememberMe, setEnteredRememberMe] = useState(false);
    const [enteredUsernameError, setUsernameError] = useState(false);
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [errorMessageDisplay, setErrorMessageDisplay] = useState(false);
    const [t] = useTranslation();

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
                        setErrorMessageDisplay(false);
                        if (key===null){
                            setError();
                        }else{
                        //save key local storage
                        setLogInState(true);
                        }
                    }
                })
        }
        return () => {
            mountedUser = false;
        }
    }, [loginCredentials]);

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

    const setError = () =>{
        setErrorMessageDisplay(true);
        setTimeout(()=>{
            setErrorMessageDisplay(false);
        },5000);
    }
    const submitHandler = (event) => {
        event.preventDefault();
        if (!(enteredPasswordError || enteredUsernameError) && enteredUsername.length !== 0 && enteredPassword.length !== 0) {
            setCredentials({username: enteredUsername, password: enteredPassword});
        }else{
            setError();
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