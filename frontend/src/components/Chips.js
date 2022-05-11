import {Link} from "react-router-dom";
import {IconButton} from "@mui/material";

const Chips = (props) => {
    return (
        <Link to={props.url}>
            <IconButton size={"small"} className="rounded-full capitalize whitespace-nowrap text-center text-gray-700 bg-yellow-50 shadow-md p-2 hover:bg-yellow-100 hover:shadow-amber-400/50">
                {props.text}
            </IconButton>
        </Link>);
}

export default Chips;