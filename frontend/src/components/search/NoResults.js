import {useTranslation} from "react-i18next";

const NoResults = () => {
    const {t} = useTranslation();

    return (
        <div className="flex flex-col justify-center items-center space-y-1.5 text-gray-400">
            <img className="w-36" alt={t('search_no_results')} src={require('../../images/PopCultLogoX.webp')}/>
            <span>{t('search_no_results')}</span>
        </div>
    )
}

export default NoResults;