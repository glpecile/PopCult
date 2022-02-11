import {NavLink} from "react-router-dom";

const DropdownMenu = () => {
    return (
        <li className="z-50 nav-item dropdown transition duration-500 ease-in-out transform hover:-translate-y-1 hovƒer:scale-105">
            <div className="nav-link dropdown-toggle active text-lg lg:text-right"
                 id="navbarDropdownMenuLink"
                 role="button"
                 data-bs-toggle="dropdown" aria-expanded="false">
                username
            </div>
            <ul className="dropdown-menu dropdown-menu-light" aria-labelledby="navbarDropdownMenuLink">
                <li>
                    <NavLink to='/' className="dropdown-item">
                        Home
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/a'>
                        Profile
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/b'>
                        My Lists
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/c'>
                        Notifications
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/d'>
                        Admin Panel
                    </NavLink>
                </li>
                <li>
                    <NavLink className="dropdown-item" to='/user/e'>
                        Sign Out
                    </NavLink>
                </li>
            </ul>
        </li>);
}
export default DropdownMenu;