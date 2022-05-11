import {NavLink} from "react-router-dom";
import DropdownMenu from "./DropdownMenu";
import {useTranslation} from "react-i18next";
import {motion} from "framer-motion";
import {useContext} from "react";
import AuthContext from "../../store/AuthContext";
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import SearchInput from "./SearchInput";

const Navbar = () => {
    let animation = "transition duration-200 ease-in-out transform hover:-translate-1 active:scale-90 hover:scale-110";
    const [t] = useTranslation();
    const SwitchOnLoggedUser = () => {
        const isUserPresent = useContext(AuthContext).isLoggedIn;

        return (
            <>
                {isUserPresent &&
                    <DropdownMenu/>}
                {!isUserPresent &&
                    (
                        <li className="nav-item flex justify-end">
                            <NavLink className={"nav-link active text-lg " + animation} aria-current="page" to='/login'>
                                {t('nav_sign_in')}
                            </NavLink>
                        </li>
                    )
                }
            </>);
    }
    return (
        <nav
            className="relative navbar navbar-dark navbar-expand-lg w-full mb-3 text-white shadow-md bg-gradient-to-r from-amber-500 to-violet-900">
            <div className="container-fluid flex sm:px-12 px-16">
                <NavLink className="navbar-brand m-0 p-0" to='/'>
                    <motion.img
                        whileHover={{rotate: '-2deg'}}
                        whileTap={{scale: 0.9}}
                        className="w-52"
                        src={require('../../images/PopCultCompleteLogo.png')}
                        alt="popcult_text_logo"/>
                </NavLink>
                <button className={"navbar-toggler " + animation}
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbar"
                        aria-expanded="false"
                        aria-label="Toggle navigation">
                    <MenuOutlinedIcon className="navbar-toggler-icon"/>
                </button>
                <div className="collapse navbar-collapse space-x-8 text-center justify-end" id="navbar">
                    <ul className="navbar-nav">
                        <li className="nav-item flex justify-end">
                            <NavLink className={"nav-link active text-lg " + animation} aria-current="page"
                                     to='/media/films'>
                                {t('nav_films')}
                            </NavLink>
                        </li>
                        <li className="nav-item flex justify-end">
                            <NavLink className={"nav-link active text-lg " + animation} aria-current="page"
                                     to='/media/series'>
                                {t('nav_series')}
                            </NavLink>
                        </li>

                        <li className="nav-item flex justify-end">
                            <NavLink className={"nav-link active text-lg " + animation} aria-current="page" to='/lists'>
                                {t('nav_lists')}
                            </NavLink>
                        </li>
                        <SwitchOnLoggedUser/>
                        <li className="nav-item flex justify-end">
                            <SearchInput/>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;