import {NavLink} from "react-router-dom";
import DropdownMenu from "./DropdownMenu";
import {useTranslation} from "react-i18next";
import {motion} from "framer-motion";

const Navbar = () => {
    const [t] = useTranslation();
    const SwitchOnLoggedUser = () => {
        const isUserPresent = localStorage.hasOwnProperty("user");

        return (
            <>
                {isUserPresent &&
                    <DropdownMenu/>}
                {!isUserPresent &&
                    (
                        <motion.li whileHover={{scale: 1.1}}
                                   whileTap={{scale: 0.9}}
                                   className="nav-item">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/login'>
                                {t('nav_sign_out')}
                            </NavLink>
                        </motion.li>)}
            </>);
    }
    return (
        <nav
            className="mb-3 relative navbar navbar-expand-lg w-full navbar-dark bg-dark text-white shadow-md bg-gradient-to-r from-amber-500 to-purple-900">
            <div className="container-fluid flex sm:px-12 px-16">
                <NavLink className="navbar-brand m-0 p-0" to='/'>
                    <motion.img
                        whileHover={{rotate: '-2deg'}}
                        whileTap={{scale: 0.9}}
                        className="w-52"
                        src={require('../../images/PopCultCompleteLogo.png')}
                        alt="popcult_text_logo"/>
                </NavLink>
                <motion.button whileHover={{scale: 1.1}}
                               whileTap={{scale: 0.9}}
                               className="navbar-toggler"
                               type="button"
                               data-bs-toggle="collapse"
                               data-bs-target="#navbarScroll"
                               aria-controls="navbarScroll"
                               aria-expanded="false" aria-label="Toggle navigation">
                    <i className="fas fa-bars navbar-toggler-icon">
                    </i>
                </motion.button>
                <div
                    className="collapse navbar-collapse flex space-x-8 justify-center items-center text-center sm:justify-end"
                    id="navbarScroll">
                    <ul className="navbar-nav ms-auto my-2 my-lg-0">
                        <motion.li whileHover={{scale: 1.1}}
                                   whileTap={{scale: 0.9}}
                                   className="nav-item">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/media/films'>
                                {t('nav_films')}
                            </NavLink>
                        </motion.li>
                        <motion.li whileHover={{scale: 1.1}}
                                   whileTap={{scale: 0.9}}
                                   className="nav-item">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/media/series'>
                                {t('nav_series')}
                            </NavLink>
                        </motion.li>
                        <motion.li whileHover={{scale: 1.1}}
                                   whileTap={{scale: 0.9}}
                                   className="nav-item">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/lists'>
                                {t('nav_lists')}
                            </NavLink>
                        </motion.li>
                        <SwitchOnLoggedUser/>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;