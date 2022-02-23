import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import BrandingImg from "./BrandingImg";

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
                    <div className="w-full max-w-sm mx-auto p-2.5 font-sans rounded-lg shadow-lg bg-white">
                        {props.children}
                    </div>
                </div>
            </div>
        </>
    )
}
export default RegisterCard;