import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import IconLinkCard from "../../../components/IconLinkCard";

export default function AdminPanel() {
    const isAdmin = true;
    const {t} = useTranslation();
    return (<>
        <Helmet>
            <title>{t('admin_title')}</title>
        </Helmet>
        {/*Title*/}
        <h1 className="text-center display-5 fw-bolder py-4">
            {t('admin_header')}
        </h1>
        <div className="grid lg:grid-cols-2 grid-cols-1 gap-5">
            {/*Link to Reports*/}
            <IconLinkCard icon={<i
                className="fas fa-exclamation-circle text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>}
                          linkTo='/admin/reports'
                          title={t('report_title_plural')}
                          description={t('admin_reports_description')}/>
            {/*Link to Bans*/}
            <IconLinkCard
                icon={<i className="fas fa-user-alt-slash text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>}
                linkTo='/admin/bans'
                title={t('bans_title')}
                description={t('admin_bans_description')}/>
            {/*Link to Mods*/}
            {isAdmin && <IconLinkCard
                icon={<i className="fas fa-users text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>}
                linkTo='/admin/mods'
                title={t('mods_title')}
                description={t('admin_mods_description')}/>
            }
        </div>
    </>);
}