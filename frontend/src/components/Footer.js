import {Link} from "react-router-dom";

const Footer = (props) => {
    return (
        <div className={"flex justify-center text-base font-light py-3 inset-x-0 bottom-0 " + props.styleName}>
            © 2021 Copyright:
            <Link className="pl-1 text-center font-light text-purple-400 hover:text-purple-900 text-decoration-none"
                  to='/'>PopCult.com</Link>
        </div>
    );
}

export default Footer;