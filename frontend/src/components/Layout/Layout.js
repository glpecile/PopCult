import Navbar from "../navigation/Navbar";
import Footer from "../navigation/Footer";
import {useLocation} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import ContextProviderWrapper from "../../store/ContextProviderWrapper";

function Layout(props) {
    const location = useLocation();
    const [layoutVisibility, setLayoutVisibility] = useState(true);
    const {t} = useTranslation();

    const isLayoutVisible = useCallback(() => {
        return location.pathname !== '/login' && location.pathname !== '/register'
            && location.pathname !== '/recovery' && location.pathname !== '/resetPassword' && location.pathname !== '/register/success' && location.pathname !== '/register/expired'
            && location.pathname !== '/requestMod';
    }, [location.pathname]);

    useEffect(() => {
        setLayoutVisibility(isLayoutVisible());
    }, [isLayoutVisible]);

    return (
        <ContextProviderWrapper>
            <Helmet>
                <title>{t('default_title')}</title>
            </Helmet>
            {layoutVisibility ?
                (
                    <div className="bg-gray-50">
                        <div className="flex flex-col justify-between min-h-screen">
                            <Navbar/>
                            <main className="flex-grow lg:max-w-screen-lg md:max-w-screen-md max-w-screen-sm container mx-auto">{props.children}</main>
                            <Footer styleName="text-slate-900"/>
                        </div>
                    </div>
                )
                :
                (
                    <div className="bg-gradient-to-r from-amber-500 to-violet-900">
                        <div className="flex flex-col justify-between min-h-screen">
                            <main>{props.children}</main>
                            <Footer styleName="text-slate-50"/>
                        </div>
                    </div>
                )
            }
        </ContextProviderWrapper>
    );
}

export default Layout;