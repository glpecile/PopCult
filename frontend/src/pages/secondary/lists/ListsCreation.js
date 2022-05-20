import NewListStepper from "../../../components/lists/creation/NewListStepper";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";

const ListsCreation = () => {
    const {t} = useTranslation();

    return (<>
            <Helmet>
                <title>{t('lists_creation_title')}</title>
            </Helmet>
            <div className="row g-3 p-2 my-8 bg-white shadow-lg rounded-lg">
                <NewListStepper/>
            </div>
        </>
    );
}

export default ListsCreation;