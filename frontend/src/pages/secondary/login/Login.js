import LoginCard from "../../../components/login/LoginCard";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState, useContext} from "react";
import {useTranslation} from "react-i18next";
import {Checkbox, IconButton} from "@mui/material";
import UserService from "../../../services/UserService";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import AuthContext from "../../../store/AuthContext";

export default function Login() {
    const location = useLocation()
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredPassword, setEnteredPassword] = useState('');
    const [passwordShown, setPasswordShown] = useState(false);
    const [enteredRememberMe, setEnteredRememberMe] = useState(false);
    const [enteredUsernameError, setUsernameError] = useState(false);
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [errorMessageDisplay, setErrorMessageDisplay] = useState(false);

    const [t] = useTranslation();
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);

    useEffect(() => {
        if (authContext.isLoggedIn) {
            if (location.state && location.state.url) {
                navigate(location.state.url);
            } else {
                navigate('/');
            }
        }
    }, [navigate, authContext.isLoggedIn, location.state]);

    const UsernameChangeHandler = (event) => {
        setEnteredUsername(event.target.value);
        event.target.validity.valid ? setUsernameError(false) : setUsernameError(true);
    };
    const PasswordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
        event.target.validity.valid ? setPasswordError(false) : setPasswordError(true);

    };
    const RememberMeChangeHandler = () => {
        setEnteredRememberMe(!enteredRememberMe);
    };

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setErrorMessageDisplay(false);
            }, 5000);
            return () => clearTimeout(timeOut);
        }
        , [errorMessageDisplay]);

    const setErrorMessage = () => {
        setErrorMessageDisplay(true);
    }

    const togglePassword = () => {
        setPasswordShown(!passwordShown);
    };

    const submitHandler = (event) => {
        event.preventDefault();

        async function login() {
            try {
                const key = await UserService.login({username: enteredUsername, password: enteredPassword})
                setErrorMessageDisplay(false);
                authContext.onLogin(key, enteredUsername);
                enteredRememberMe === true ? localStorage.setItem("userAuthToken", JSON.stringify(key)) : sessionStorage.setItem("userAuthToken", JSON.stringify(key));
            } catch (error) {
                console.log(error);
                setErrorMessage();
            }

        }

        if (!(enteredPasswordError || enteredUsernameError) && enteredUsername.length !== 0 && enteredPassword.length !== 0) {
            login();
        } else {
            setErrorMessage();
        }
    };

    return <LoginCard>
        <form className="my-1.5 relative" onSubmit={submitHandler} noValidate={true}>
            <div className="flex flex-col">
                {/* Username */}
                <label className="py-2 text-semibold w-full">
                    {t('login_username')}
                </label>
                <input className="w-full rounded active:none shadow-sm accent-violet-400" type="text"
                       value={enteredUsername}
                       pattern="[a-zA-Z0-9]+" minLength={1} maxLength={100}
                       onChange={UsernameChangeHandler}/>

                {/* Password */}
                <IconButton onClick={togglePassword} className="absolute top-28 my-3.5 right-0.5">
                    {passwordShown ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                </IconButton>
                <label className="py-2 text-semibold w-full">
                    {t('login_password')}
                </label>
                <input className="w-full rounded active:none shadow-sm" type={passwordShown ? "text" : "password"}
                       value={enteredPassword}
                       minLength={8} maxLength={100}
                       onChange={PasswordChangeHandler}/>

                {/* Remember me */}
                <div className="flex pt-2 justify-start">
                    <label className="space-x-2 cursor-pointer">
                        <Checkbox checked={enteredRememberMe} label={t('login_remember_me')}
                                  onChange={RememberMeChangeHandler} color="secondary"/>
                        <span className="text-semibold">
                            {t('login_remember_me')}
                        </span>
                    </label>
                </div>

                {errorMessageDisplay && <div className="py-1 text-semibold text-danger w-full">
                    {t('login_error')}
                </div>}

                <button className="btn btn-secondary bg-violet-500 my-2 w-full shadow-md hover:shadow-violet-400" type="submit">
                    {t('login_button')}
                </button>

                <Link className="text-center text-slate-700 text-sm uppercase hover:text-violet-800" to="/recovery">
                    {t('login_recovery')}
                </Link>
            </div>
        </form>
    </LoginCard>
}