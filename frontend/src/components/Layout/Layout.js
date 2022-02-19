import Navbar from "../navigation/Navbar";
import Footer from "../navigation/Footer";
import {useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import {Helmet} from "react-helmet";
import {useTranslation} from "react-i18next";

function Layout(props) {
    const location = useLocation()
    const [layoutVisibility, setLayoutVisibility] = useState(true);
    const {t} = useTranslation();

    useEffect(() => {
        setLayoutVisibility(location.pathname !== '/login' && location.pathname !== '/register');
    }, [location]);

    return <>
        {layoutVisibility &&
            (<>
                <Helmet>
                    <title>{t('default_title')}</title>
                </Helmet>
                <div className="bg-gray-50">
                    <div className="flex flex-col justify-between min-h-screen">
                        <Navbar/>
                        <main className="col-8 offset-2 flex-grow mb-auto">
                            {props.children}
                        </main>
                        <Footer styleName="text-slate-900"/>
                    </div>
                </div>
            </>)
        }
        {!layoutVisibility &&
            <div className="bg-gradient-to-r from-amber-500 to-purple-900">
                <div className="flex flex-col justify-between min-h-screen">
                    <main>{props.children}</main>
                    <Footer styleName="text-slate-50"/>
                </div>
            </div>}
    </>
}

export default Layout;