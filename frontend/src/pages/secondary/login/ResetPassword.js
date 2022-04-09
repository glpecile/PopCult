import RecoveryCard from "../../../components/login/RecoveryCard";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import FadeIn from "../../../components/animation/FadeIn";
import {motion} from "framer-motion";
import {useLocation, useNavigate} from "react-router-dom";

export default function ResetPassword() {
    const navigate = useNavigate();
    const query = new URLSearchParams(useLocation().search)
    let token = query.get("token");
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [enteredRepeatedPassword, setEnteredRepeatedPassword] = useState('');
    const [enteredRepeatedPasswordError, setRepeatedPasswordError] = useState(false);
    const [alertDisplay, setAlertDisplay] = useState(false);
    const [badAttempt, setBadAttempt] = useState(false);
    const [t] = useTranslation();

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

    const submitHandler = (event) => {
        console.log("password sent: " + enteredPassword);
        event.preventDefault();
        if (!enteredPasswordError && !enteredRepeatedPasswordError && enteredPassword && token)
            try {
                UserService.resetPassword({token: token, newPassword: enteredPassword});
                navigate("/login");
            } catch (error) {
                if (error.response.data.message.localeCompare("Token not found") || error.response.data.message.localeCompare("Token no encontrado") || error.response.data.message.localeCompare("Invalid token") || error.response.data.message.localeCompare("Token inválido")) {
                    setBadAttempt(true)
                }
            }
        else setBadAttempt(true);
    }

    useEffect(() => {
            const timeOut = setTimeout(() => {
                if (alertDisplay) {
                    setAlertDisplay(false);
                }
                if (badAttempt) {
                    setBadAttempt(false);
                }
            }, 3000)
            return () => clearTimeout(timeOut);
        }
        , [alertDisplay, badAttempt]);

    return (
        <RecoveryCard title={t('recovery_title')} heading={t('recovery_reset_password')}>
            <form className="m-0 p-0" onSubmit={submitHandler} noValidate={true}>
                <div className="flex flex-col justify-center items-center">
                    {/* Password */}
                    <div className="py-1 text-semibold w-full">
                        <div className="relative">
                            {enteredPasswordError ?
                                (<span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"
                                       onClick={() => setAlertDisplay(true)}>
                                <i className="fas fa-exclamation-circle"/>
                            </span>) :
                                (<span className="absolute inset-y-0 -top-10 right-3 flex items-center pl-2"
                                       onClick={() => setAlertDisplay(true)}>
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
                                <button type="button" onClick={() => setAlertDisplay(false)}>
                                    <i className="fas fa-times hover:text-gray-800"/>
                                </button>
                            </span>
                                <small key="text" id="passwordHelpBlock"
                                       className="form-text text-muted whitespace-pre-wrap">
                                    {t('register_password_hint')}
                                </small>
                            </motion.div>
                        </motion.div>
                    </FadeIn>

                    {/* Repeated Password */}
                    <div className="py-1 text-semibold w-full">
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
                </div>

                <div className="flex justify-between my-2">
                    <div>
                        {
                            badAttempt &&
                            <p className="text-red-500 text-xs italic my-1.5 mt-2">
                                {t('recovery_password_error')}
                            </p>
                        }
                    </div>
                    <div className="flex justify-end">
                        <button className="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded" type="submit">
                            {t('recovery_reset_password_button')}
                        </button>
                    </div>
                </div>
            </form>
        </RecoveryCard>
    );
}