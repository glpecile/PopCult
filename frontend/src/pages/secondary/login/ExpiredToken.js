import BrandingImg from "../../../components/login/BrandingImg";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useCallback, useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import {Helmet} from "react-helmet-async";

const ExpiredToken = () => {
    const {t} = useTranslation();
    const [enteredEmail, setEnteredEmail] = useState('');
    const [enteredEmailError, setEnteredEmailError] = useState(false);
    const [emailError, setEmailError] = useState(false);
    const [submit, setSubmit] = useState(false);
    const navigate = useNavigate();

    const EmailChangeHandler = (event) => {
        setEnteredEmail(event.target.value);
        setEmailError(false);
        event.target.validity.valid ? setEnteredEmailError(false) : setEnteredEmailError(true);
    }
    const resendEmail = useCallback(async () => {
        let hasError = false
        try {
            await UserService.sendVerificationToken(enteredEmail);
        } catch (error) {
            console.log(error);
            hasError = true;
            setEmailError(true);
        }
        if (!hasError) navigate('/register/success');
        setSubmit(false);
    }, [enteredEmail, navigate]);

    useEffect(() => {
        if (submit) {
            resendEmail();
        }
    }, [submit, resendEmail]);

    const submitHandler = (event) => {
        event.preventDefault();
        if (!enteredEmailError)
            setSubmit(true);
    }
    return (
        <>
            <Helmet>
                <title>{t('register_title')}</title>
            </Helmet>
            <BrandingImg/>
            <div className="flex-grow mt-3">
                <div className="w-full max-w-sm mx-auto p-2.5 font-sans rounded-lg shadow-lg bg-white">
                    <h2 className="text-3xl m-3 text-center">
                        {t('expiredToken_title')}
                    </h2>
                    <div className="whitespace-pre-wrap m-3 mb-0">
                        {t('expiredToken_body')}
                    </div>
                    <form className="m-3 mb-0 p-0" onSubmit={submitHandler} noValidate={true}>
                        <div className="flex flex-col justify-center items-center">
                            <div className="pb-1 text-semibold w-full">
                                <input type="text"
                                       className={"w-full rounded active:none " + (enteredEmailError || emailError ? "border-2 border-rose-500" : "")}
                                       pattern="[^@]+@[^@]+\.[^@]+" minLength={6} maxLength={100}
                                       defaultValue={enteredEmail}
                                       onChange={EmailChangeHandler}/>
                                {enteredEmailError &&
                                    <p className="text-red-500 text-xs italic whitespace-pre-wrap">
                                        {t('register_email_validation')}
                                    </p>}
                                {emailError &&
                                    <p className="text-red-500 text-xs italic my-1.5">
                                        {t('expiredToken_emailError')}
                                    </p>
                                }
                            </div>
                        </div>
                        <div className='flex justify-end'>
                            <button type="submit"
                                    className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded ">
                                {t('send')}
                            </button>
                        </div>
                    </form>
                    <div className="whitespace-pre-wrap mx-3 mb-1 mb-0">
                        {t('expiredToken_greet')}
                    </div>
                </div>
            </div>
        </>);
}
export default ExpiredToken;