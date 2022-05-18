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
        <h1 className="text-center text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide py-4">
            {t('admin_header')}
        </h1>
        <div className="grid lg:grid-cols-2 grid-cols-1 gap-5">
            {/* Link to Reports */}
            {<IconLinkCard icon={<ReportOutlinedIcon fontSize="large"
                                                     className="text-violet-500 group-hover:text-violet-900 mt-3"/>}
                           linkTo='/admin/reports'
                           title={t('report_title_plural')}
                           description={t('admin_reports_description')}/>}
            {/* Link to Bans */}
            <IconLinkCard
                icon={<GroupRemoveOutlinedIcon fontSize="large"
                                               className="text-violet-500 group-hover:text-violet-900 mt-3"/>}
                linkTo='/admin/bans'
                title={t('bans_title')}
                description={t('admin_bans_description')}/>
            {/* Link to Mods */}
            {isAdmin && <IconLinkCard
                icon={<GroupOutlinedIcon fontSize="large"
                                         className="text-violet-500 group-hover:text-violet-900 mt-3"/>}
                linkTo='/admin/mods'
                title={t('mods_title')}
                description={t('admin_mods_description')}/>
            }
        </div>
    </>);
}