import {Link} from "react-router-dom";

const IconLinkCard = (props) => {
    return (<div
        className="group flex flex-col bg-white items-center shadow-md hover:shadow-indigo-500/50 rounded-lg p-3 transition duration-300 ease-in-out hover:bg-purple-50 transform hover:-translate-1 hover:scale-105 active:scale-90">
        {props.icon}
        <h2 className="text-2xl text-center py-2 pb-2.5">
            <Link className="stretched-link text-purple-500 group-hover:text-purple-900"
                  to={props.linkTo}>
                <b>
                    {props.title}
                </b>
            </Link>
        </h2>
        <p className="p-2.5 m-1.5 text-center">
            {props.description}
        </p>
    </div>);
}
export default IconLinkCard;