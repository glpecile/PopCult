import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

const Footer = (props) => {
    const {i18n} = useTranslation();

    const changeLanguage = (lng) => {
        i18n.changeLanguage(lng);
    };

    return (
        <div className="relative flex inset-x-0 bottom-0 justify-center my-3.5">
            <div className={"flex text-base font-light " + props.styleName}>
                © 2022 Copyright:
                <Link className={"pl-1 text-center font-light hover:text-purple-900 text-decoration-none " + props.styleName}
                      to='/'>PopCult</Link>
            </div>
            <div className="absolute flex space-x-2 right-4">
                <button
                    className={"hover:text-purple-900 transition duration-300 ease-in-out transform hover:-translate-y-1 hover:scale-105 " + props.styleName}
                    onClick={() => changeLanguage('es')}>
                    ES
                </button>
                <button
                    className={"hover:text-purple-900 transition duration-300 ease-in-out transform hover:-translate-y-1 hover:scale-105 " + props.styleName}
                    onClick={() => changeLanguage('en')}>
                    EN
                </button>
            </div>
        </div>
    );
}

export default Footer;