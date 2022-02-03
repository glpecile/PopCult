import {Link} from "react-router-dom";

function Navbar() {
    return (<header>
        <div>React Routing</div>
        <nav>
            <ul>
                <li>
                    <Link to='/'>Home</Link>
                </li>
                <li>
                    <Link to='/media/films'>Films</Link>
                </li>
                <li>
                    <Link to='/media/series'>Series</Link>
                </li>
                <li>
                    <Link to='/lists'>Lists</Link>
                </li>
            </ul>
        </nav>
    </header>);
}

export default Navbar;