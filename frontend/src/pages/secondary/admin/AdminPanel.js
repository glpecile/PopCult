import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import {Helmet} from "react-helmet-async";

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
            <div
                className="group flex flex-col bg-white items-center shadow-md hover:shadow-indigo-500/50 rounded-lg p-3 transition duration-300 ease-in-out hover:bg-purple-50 transform hover:-translate-y-1 hover:scale-110">
                <i className="fas fa-exclamation-circle text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>
                <h2 className="text-2xl text-center py-2 pb-2.5">
                    <Link className="stretched-link text-purple-500 group-hover:text-purple-900"
                          to='/admin/reports'>
                        <b>
                            {t('report_title_plural')}
                        </b>
                    </Link>
                </h2>
                <p className="p-2.5 m-1.5 text-center">
                    {t('admin_reports_description')}
                </p>
            </div>
            {/*Link to Bans*/}
            <div
                className="group flex flex-col bg-white items-center shadow-md hover:shadow-indigo-500/50 rounded-lg p-3 transition duration-300 ease-in-out hover:bg-purple-50 transform hover:-translate-y-1 hover:scale-110">
                <i className="fas fa-user-alt-slash text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>
                <h2 className="text-2xl text-center py-2 pb-2.5">
                    <Link className="stretched-link text-purple-500 group-hover:text-purple-900"
                          to='/admin/bans'>
                        <b>
                            {t('bans_title')}
                        </b>
                    </Link>
                </h2>
                <p className="p-2.5 m-1.5 text-center">
                    {t('admin_bans_description')}
                </p>
            </div>
            {/*Link to Mods*/}
            {isAdmin &&
                <div
                    className="group flex flex-col bg-white items-center shadow-md hover:shadow-indigo-500/50 rounded-lg p-3 transition duration-300 ease-in-out hover:bg-purple-50 transform hover:-translate-y-1 hover:scale-110">
                    <i className="fas fa-users text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>
                    <h2 className="text-2xl text-center py-2 pb-2.5">
                        <Link className="stretched-link text-purple-500 group-hover:text-purple-900"
                              to='/admin/mods'>
                            <b>
                                {t('mods_title')}
                            </b>
                        </Link>
                    </h2>
                    <p className="p-2.5 m-1.5 text-center">
                        {t('admin_mods_description')}
                    </p>
                </div>}
        </div>
    </>);
}