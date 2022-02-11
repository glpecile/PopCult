import {Link} from "react-router-dom";

const Chips = (props) => {
    return (
        <Link to={props.url}>
            <button
                className="rounded-full capitalize whitespace-nowrap text-center text-gray-700 bg-yellow-50 shadow-md p-2 hover:bg-yellow-100 hover:shadow-lg">
                {props.text}
            </button>
        </Link>);
}

export default Chips;