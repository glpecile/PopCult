import {NavLink} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {motion} from "framer-motion";

const DropdownMenu = () => {
    const [t] = useTranslation();
    return (
        <motion.li whileHover={{scale: 1.1}}
                   whileTap={{scale: 0.9}}
                   className="z-50 nav-item dropdown">
            <div className="nav-link dropdown-toggle active text-lg lg:text-right"
                 id="navbarDropdownMenuLink"
                 role="button"
                 data-bs-toggle="dropdown" aria-expanded="false">
                username
            </div>
            <ul className="dropdown-menu dropdown-menu-light" aria-labelledby="navbarDropdownMenuLink">
                <li>
                    <NavLink to='/' className="dropdown-item">
                        {t('nav_home')}
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/a'>
                        {t('nav_profile')}
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/b'>
                        {t('nav_my_lists')}
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/c'>
                        {t('nav_notifications')}
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/admin'>
                        {t('nav_admin')}
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/e'>
                        {t('nav_sign_out')}
                    </NavLink>
                </li>
            </ul>
        </motion.li>);
}
export default DropdownMenu;