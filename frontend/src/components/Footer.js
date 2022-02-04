import {Link} from "react-router-dom";

function Footer() {
    return (
        <div className="flex py-3">
            <div className="flex flex-1 justify-center items-center text-base text-center font-light pl-16">
                © 2021 Copyright:<Link className="pl-1 text-center font-light hover:text-purple-900 text-decoration-none"
                                    to='/'>
                PopCult.com</Link>
            </div>
        </div>
    );
}

export default Footer;