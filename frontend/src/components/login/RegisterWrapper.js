import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";

const RegisterWrapper = (props) => {
    const [t] = useTranslation();
    return (
        <>
            <Helmet>
                <title>{t('register_title')}</title>
            </Helmet>
            <div>
                {/*    Logo and Card Title */}
                <Link className="flex justify-center items-center pt-4" to='/'>
                    <img className="w-32 scale-105" src={require("../../images/PopCultLogo.png")} alt="popcult_logo"/>
                </Link>
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
export default RegisterWrapper;