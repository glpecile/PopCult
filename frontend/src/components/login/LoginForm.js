import {useState} from "react";
import {useTranslation} from "react-i18next";

function LoginForm(props) {
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredRememberMe, setEnteredRememberMe] = useState(false);
    const [enteredUsernameError, setUsernameError] = useState(false);
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [errorMessageDisplay, setErrorMessageDisplay] = useState(false);
    const [t] = useTranslation();

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

    const submitHandler = (event) => {
        event.preventDefault();
        if (!(enteredPasswordError || enteredUsernameError) && enteredUsername.length !== 0 && enteredPassword.length !== 0) {
            props.onSuccessfullLogIn();
        } else {
            setErrorMessageDisplay(true);
        }
    };

    return (
        <form className="my-1.5" onSubmit={submitHandler} noValidate={true}>
            <div className="flex flex-col">
                <label className="py-2 text-semibold w-full">
                    {t('login_username')}
                </label>
                <input className="w-full rounded active:none shadow-sm accent-purple-400" type="text" value={enteredUsername}
                       pattern="[a-zA-Z0-9]+" minLength={4} maxLength={100}
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
    );
}

export default LoginForm;