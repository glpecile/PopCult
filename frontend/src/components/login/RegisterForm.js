import {useState} from "react";
import {useTranslation} from "react-i18next";
import FadeIn from "../animation/FadeIn";
import {motion} from "framer-motion";


const RegisterForm = (props) => {
    const [enteredName, setEnteredName] = useState('');
    const [enteredNameError, setNameError] = useState(false);
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredUsernameError, setUsernameError] = useState(false);
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [enteredRepeatedPassword, setEnteredRepeatedPassword] = useState('');
    const [enteredRepeatedPasswordError, setRepeatedPasswordError] = useState(false);
    const [enteredEmail, setEnteredEmail] = useState('');
    const [enteredEmailError, setEmailError] = useState(false);
    const [alertDisplay, setAlertDisplay] = useState(false);
    const [badAttempt, setBadAttempt] = useState(false);
    const [t] = useTranslation();

    const NameChangeHandler = (event) => {
        setEnteredName(event.target.value);
        event.target.validity.valid ? setNameError(false) : setNameError(true);
    };

    const UsernameChangeHandler = (event) => {
        setEnteredUsername(event.target.value);
        event.target.validity.valid ? setUsernameError(false) : setUsernameError(true);
    };

    const PasswordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
        event.target.validity.valid ? setPasswordError(false) : setPasswordError(true);
        if (enteredRepeatedPassword.localeCompare('') !== 0) {
            (event.target.value.localeCompare(enteredRepeatedPassword) === 0) ? setRepeatedPasswordError(false) : setRepeatedPasswordError(true);
        }
    };
    const RepeatedPasswordChangeHandler = (event) => {
        setEnteredRepeatedPassword(event.target.value);
        (enteredPassword.localeCompare(event.target.value) === 0) ? setRepeatedPasswordError(false) : setRepeatedPasswordError(true);
    };
    const EmailChangeHandler = (event) => {
        setEnteredEmail(event.target.value);
        event.target.validity.valid ? setEmailError(false) : setEmailError(true);
    };

    const displayAlert = () => {
        setAlertDisplay(true);
        setTimeout(() => {
            setAlertDisplay(false);
        }, 5000);
    }
    const formIsValid = () => {
        const hasErrors = !(enteredEmailError || enteredUsernameError || enteredNameError || enteredPasswordError || enteredRepeatedPasswordError);
        const isEmpty = !(enteredEmail.length === 0 || enteredUsername.length === 0 || enteredName.length === 0 || enteredPassword.length === 0 || enteredRepeatedPassword.length === 0);
        return hasErrors && isEmpty;
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if (formIsValid()) {
            props.onSuccessfulRegister({
                email: enteredEmail,
                username: enteredUsername,
                password: enteredPassword,
                name: enteredName
            });
        }else{
            setBadAttempt(true);
            setTimeout(() => {
                setBadAttempt(false);
            }, 5000);
        }
    }

    return (
        <form className="m-0 p-0" onSubmit={submitHandler} noValidate={true}>
            {/*Email*/}
            <div className="flex flex-col justify-center items-center">
                <div className="pb-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        {enteredEmailError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <label
                            className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                            {t('register_email')}
                        </label>
                        <input type="text"
                               className={"w-full rounded active:none " + (enteredEmailError || props.emailExists ? "border-2 border-rose-500" : "")}
                               pattern="[^@]+@[^@]+\.[^@]+" minLength={6} maxLength={100} defaultValue={enteredEmail}
                               onChange={EmailChangeHandler}/>
                    </div>
                    {/* Se agrega whitespace-pre-wrap como clase para que i18next pueda leer los \n */}
                    {enteredEmailError &&
                        <p className="text-red-500 text-xs italic my-1.5 whitespace-pre-wrap">
                            {t('register_email_validation')}
                        </p>}
                    {props.emailExists &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_email_exists')}
                        </p>
                    }
                </div>

                {/*Pass*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        {enteredPasswordError ?
                            (<span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"
                                   onClick={displayAlert}>
                                <i className="fas fa-exclamation-circle"/>
                            </span>) :
                            (<span className="absolute inset-y-0 -top-10 right-3 flex items-center pl-2"
                                   onClick={displayAlert}>
                                    <i className="fas fa-question-circle"/>
                            </span>)}
                        <label
                            className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                            {t('register_password')}
                        </label>
                        <input type="password"
                               className={"w-full rounded active:none " + (enteredPasswordError ? "border-2 border-rose-500" : "")}
                               minLength={8} maxLength={100}
                               defaultValue={enteredPassword} onChange={PasswordChangeHandler}/>
                    </div>
                    {enteredPasswordError &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_password_error')}
                        </p>}
                </div>

                <FadeIn isActive={alertDisplay}>
                    <motion.div key="modal" className="py-1 px-2.5 collapse show" id="alert">
                        <motion.div key="icon"
                                    className="alert bg-purple-200/95 text-gray-500 d-flex align-items-center shadow-md"
                                    role="alert">
                            <span className="absolute top-0 bottom-0 right-0 px-4 py-3">
                                <button onClick={() => setAlertDisplay(false)}><i
                                    className="fas fa-times hover:text-gray-800"/></button>
                            </span>
                            <small key="text" id="passwordHelpBlock"
                                   className="form-text text-muted whitespace-pre-wrap">
                                {t('register_password_hint')}
                            </small>
                        </motion.div>
                    </motion.div>
                </FadeIn>

                {/*repPass*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        <label
                            className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                            {t('register_password_repeat')}
                        </label>
                        {enteredRepeatedPasswordError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <input type="password"
                               className={"w-full rounded active:none " + (enteredRepeatedPasswordError ? "border-2 border-rose-500" : "")}
                               minLength={8} maxLength={100} onChange={RepeatedPasswordChangeHandler}/>
                    </div>
                    {enteredRepeatedPasswordError &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_password_match_error')}
                        </p>}
                </div>
                {/*Username*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        <label
                            className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                            {t('register_username')}
                        </label>
                        {enteredUsernameError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <input type="text"
                               className={"w-full rounded active:none " + (enteredUsernameError || props.usernameExists ? "border-2 border-rose-500" : "")}
                               pattern="[a-zA-Z0-9]+" minLength={1}
                               maxLength={100} defaultValue={enteredUsername} onChange={UsernameChangeHandler}/>
                    </div>
                    {enteredUsernameError &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_username_error')}
                        </p>
                    }
                    {props.usernameExists &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_username_exists')}
                        </p>
                    }
                </div>

                {/*Name*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        <label
                            className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                            {t('register_name')}
                        </label>
                        {enteredNameError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <input type="text"
                               className={"w-full rounded active:none " + (enteredNameError ? "border-2 border-rose-500" : "")}
                               defaultValue={enteredName} onChange={NameChangeHandler}
                               minLength={3} maxLength={100} pattern="[a-zA-Z0-9\s]+"/>
                    </div>
                    {enteredNameError &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_name_error')}
                        </p>
                    }
                </div>

                <div className="py-1 px-2.5 text-semibold w-full">
                    <button className="btn btn-secondary px-2.5 mt-2 w-full" type="submit">
                        {t('register_button')}
                    </button>
                    {(badAttempt || props.registrationError) &&
                        <p className="text-red-500 text-xs text-center italic my-1.5">
                            {t('register_error')}
                        </p>
                    }
                </div>
            </div>
        </form>);
}
export default RegisterForm;