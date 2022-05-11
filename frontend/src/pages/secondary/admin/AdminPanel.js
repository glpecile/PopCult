import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import IconLinkCard from "../../../components/IconLinkCard";
import ReportOutlinedIcon from '@mui/icons-material/ReportOutlined';
import GroupRemoveOutlinedIcon from '@mui/icons-material/GroupRemoveOutlined';
import GroupOutlinedIcon from '@mui/icons-material/GroupOutlined';
import {useContext} from "react";
import AuthContext from "../../../store/AuthContext";
import {Roles} from "../../../enums/Roles";

export default function AdminPanel() {
    const isAdmin = useContext(AuthContext).role === Roles.ADMIN;

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
            {/* Link to Reports */}
            {<IconLinkCard icon={<ReportOutlinedIcon fontSize="large"
                                                     className="text-purple-500 group-hover:text-purple-900 mt-3"/>}
                           linkTo='/admin/reports?page=1&page-size=2'
                           title={t('report_title_plural')}
                           description={t('admin_reports_description')}/>}
            {/* Link to Bans */}
            <IconLinkCard
                icon={<GroupRemoveOutlinedIcon fontSize="large"
                                               className="text-purple-500 group-hover:text-purple-900 mt-3"/>}
                linkTo='/admin/bans?page=1&page-size=2'
                title={t('bans_title')}
                description={t('admin_bans_description')}/>
            {/* Link to Mods */}
            {isAdmin && <IconLinkCard
                icon={<GroupOutlinedIcon fontSize="large"
                                         className="text-purple-500 group-hover:text-purple-900 mt-3"/>}
                linkTo='/admin/mods?page=1&page-size=2'
                title={t('mods_title')}
                description={t('admin_mods_description')}/>
            }
        </div>
    </>);
}