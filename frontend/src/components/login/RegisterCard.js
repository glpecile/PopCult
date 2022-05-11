import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import BrandingImg from "./BrandingImg";
import {Link} from "react-router-dom";

const RegisterCard = (props) => {
    const [t] = useTranslation();
    return (
        <>
            <Helmet>
                <title>{t('register_title')}</title>
            </Helmet>
            <div>
                {/* Logo & Card Title */}
                <BrandingImg />
                <h2 className="font-bold text-4xl text-center text-white py-2.5">
                    {t('register_greeting')}
                </h2>
                <div className="flex-grow">
                    <div className="w-full max-w-sm mx-auto p-2.5 font-sans rounded-lg shadow-lg bg-white my-1.5">
                        {props.children}
                    </div>
                </div>
                <div className="flex justify-center">
                    <Link
                        className="text-white text-center text-sm uppercase py-2 transition duration-300 ease-in-out transform hover:translate-y-0.5 hover:scale-105 active:scale-90"
                        to='/login'>
                        {t('register_log_in')}
                    </Link>
                </div>
            </div>
        </>
    )
}
export default RegisterCard;