import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

const Footer = (props) => {
    const {i18n} = useTranslation();
    let languageButtonStyle = "hover:text-violet-900 transition duration-300 ease-in-out transform hover:-translate-1 hover:scale-110 active:scale-90 ";

    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng);
    };

    return (
        <div className="relative flex inset-x-0 bottom-0 justify-center my-3.5">
            <div className={"flex text-base font-light " + props.styleName}>
                © 2022 Copyright:
                <Link className={"pl-1 text-center font-light hover:text-violet-900 text-decoration-none " + props.styleName} to='/'>
                    PopCult
                </Link>
            </div>
            <div className="flex space-x-3.5 absolute right-4">
                <button
                    className={languageButtonStyle + props.styleName}
                    onClick={() => changeLanguage('es')}>
                    ES
                </button>
                <button
                    className={languageButtonStyle + props.styleName}
                    onClick={() => changeLanguage('en')}>
                    EN
                </button>
            </div>
        </div>
    );
}

export default Footer;