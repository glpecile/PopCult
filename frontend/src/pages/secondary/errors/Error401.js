import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";

export default function Error401() {
    const {t} = useTranslation();
    return (
        <>
            <Helmet>
                <title>{t('error401_title')}</title>
            </Helmet>
            <div className="flex-grow whitespace-pre-line">
                <div className="flex flex-wrap p-3.5 mx-auto my-auto">
                    <img className="w-80 pt-12" src={require('../../../images/PopCultLogoForbidden.png')} alt="error_image"/>
                    <div className="flex flex-col pl-8">
                        <h1 className="text-6xl font-black text-justify">
                            {t('error_private')}
                        </h1>
                        <p className="text-2xl font-semibold text-justify">
                            {t('error_private_body')}
                        </p>
                        <Link className="text-2xl font-bold text-purple-500 hover:text-purple-900" to='/'>
                            {t('error_go_home')}
                        </Link>
                    </div>
                </div>
            </div>
        </>
    );
}