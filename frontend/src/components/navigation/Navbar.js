import {NavLink} from "react-router-dom";
import DropdownMenu from "./DropdownMenu";
import {useTranslation} from "react-i18next";

const Navbar = () => {
    const [t] = useTranslation();
    const SwitchOnLoggedUser = () => {
        const isUserPresent = true;
        return (
            <>
                {isUserPresent &&
                    <DropdownMenu/>}
                {!isUserPresent &&
                    (
                        <li className="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/login'>
                                {t('nav_sign_out')}
                            </NavLink>
                        </li>)}
            </>);
    }
    return (
        <nav
            className="mb-3 relative navbar navbar-expand-lg w-full navbar-dark bg-dark text-white shadow-md bg-gradient-to-r from-amber-500 to-purple-900">
            <div className="container-fluid flex sm:px-12 px-16">
                <NavLink className="navbar-brand m-0 p-0" to='/'>
                    <img
                        className="transition duration-500 ease-in-out transform hover:-translate-y-1 hover:rotate-2 w-52"
                        src={require('../../images/PopCultCompleteLogo.png')}
                        alt="popcult_text_logo"/>
                </NavLink>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarScroll"
                        aria-controls="navbarScroll"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <i className="fas fa-bars navbar-toggler-icon">
                    </i>
                </button>
                <div
                    className="collapse navbar-collapse flex space-x-8 justify-center items-center text-center sm:justify-end"
                    id="navbarScroll">
                    <ul className="navbar-nav ms-auto my-2 my-lg-0">
                        <li className="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/media/films'>
                                {t('nav_films')}
                            </NavLink>
                        </li>
                        <li className="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/media/series'>
                                {t('nav_series')}
                            </NavLink>
                        </li>
                        <li className="nav-item transition duration-500 ease-in-out transform hover:-translate-y-1 hover:scale-105">
                            <NavLink className="nav-link active text-lg lg:text-right" aria-current="page" to='/lists'>
                                {t('nav_lists')}
                            </NavLink>
                        </li>
                        <SwitchOnLoggedUser/>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;