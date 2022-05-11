import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import BrandingImg from "./BrandingImg";

const LoginCard = (props) => {
    const [t] = useTranslation();
    return (
        <>
            <Helmet>
                <title>{t('login_title')}</title>
            </Helmet>
            <div className="flex-grow space-y-2">
                {/* Logo & Card Title */}
                <BrandingImg />
                <h2 className="font-bold text-4xl text-center text-white py-2">
                    {t('login_greeting')}
                </h2>
                <div className="w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
                    {props.children}
                </div>
                <div className="flex justify-center">
                    <Link
                        className="text-white text-center text-sm uppercase py-2 transition duration-300 ease-in-out transform hover:translate-y-0.5 hover:scale-105 active:scale-90"
                        to='/register'>
                        {t('login_sign_up')}
                    </Link>
                </div>
            </div>
        </>
    );
}

export default LoginCard;