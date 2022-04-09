import RecoveryCard from "../../../components/login/RecoveryCard";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import {Slide} from "@mui/material";
import UserService from "../../../services/UserService";

export default function Recovery() {
    const [enteredEmail, setEnteredEmail] = useState('');
    const [enteredEmailError, setEmailError] = useState(false);
    const [emailNotPresent, setEmailNotPresent] = useState(false);
    const [alert, setAlert] = useState(false);
    const [t] = useTranslation();

    const EmailChangeHandler = (event) => {
        setEnteredEmail(event.target.value);
        event.target.validity.valid ? setEmailError(false) : setEmailError(true);
    };

    async function submitHandler(event) {
        event.preventDefault();
        if (!enteredEmailError && !emailNotPresent && enteredEmail)
            try {
                await UserService.createPasswordResetToken(enteredEmail);
                setAlert(true);
            } catch (error) {
                if (error.response.data.message.localeCompare("Email does not exist") || error.response.data.message.localeCompare("El email ingresado no existe")) {
                    setEmailNotPresent(true);
                }
            } else setEmailError(true);
    }

    useEffect(() => {
        const timeOut = setTimeout(() => {
            if (alert)
                setAlert(false);
            if (emailNotPresent)
                setEmailNotPresent(false);
            if (enteredEmailError)
                setEmailError(false);
        }, 5000)
        return () => clearTimeout(timeOut);
    }, [alert, emailNotPresent, enteredEmailError]);

    return (
        <>
            <Slide direction="up" in={alert} mountOnEnter unmountOnExit>
                <div className="collapse show fixed bottom-0 sm:inset-x-64 inset-x-10 z-50 text-wrap" id="alert">
                    <div className="alert alert-secondary shadow-lg" role="alert">
                        {t('recovery_check_inbox')}
                    </div>
                </div>
            </Slide>
            <RecoveryCard title={t('recovery_title')} heading={t('recovery_greeting')}>
                <form className="m-0 p-0" onSubmit={submitHandler} noValidate={true}>
                    <div className="pb-1 text-semibold w-full">
                        <div className="relative">
                            {enteredEmailError &&
                                <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                    className="fas fa-exclamation-circle"/></span>}
                            <label
                                className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                                {t('register_email')}
                            </label>
                            <input type="text"
                                   className={"w-full rounded active:none " + (enteredEmailError || emailNotPresent ? "border-2 border-rose-500" : "")}
                                   pattern="[^@]+@[^@]+\.[^@]+" minLength={6} maxLength={100} defaultValue={enteredEmail}
                                   onChange={EmailChangeHandler}/>
                        </div>
                        {/* Se agrega whitespace-pre-wrap como clase para que i18next pueda leer los \n */}
                        {enteredEmailError &&
                            <p className="text-red-500 text-xs italic my-1.5 whitespace-pre-wrap">
                                {t('register_email_validation')}
                            </p>}
                        {emailNotPresent &&
                            <p className="text-red-500 text-xs italic my-1.5">
                                {t('recovery_unused_email')}
                            </p>
                        }
                    </div>
                    <div className="flex justify-between">
                        <Link to="/login">
                            <button type="button" className="btn btn-link text-slate-700 my-2 btn-rounded">
                                {t('recovery_cancel')}
                            </button>
                        </Link>
                        <button className="btn btn-link text-purple-500 hover:text-purple-900 my-2 btn-rounded" type="submit">
                            {t('recovery_submit')}
                        </button>
                    </div>
                </form>
            </RecoveryCard>
        </>
    );
}