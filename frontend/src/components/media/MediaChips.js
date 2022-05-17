import {Link} from "react-router-dom";
import {IconButton, Tooltip} from "@mui/material";

const MediaChips = (props) => {
    return (
        <Link to={props.url}>
            {
                props.role ?
                    <Tooltip title={props.role} placement="top" arrow>
                        <IconButton size={"small"}
                                    className="rounded-full capitalize whitespace-nowrap text-center text-gray-700 bg-yellow-50 shadow-md p-2 hover:bg-yellow-100 hover:shadow-amber-400/50">
                            {props.text}
                        </IconButton>
                    </Tooltip>
                    :
                    <IconButton size={"small"}
                                className="rounded-full capitalize whitespace-nowrap text-center text-gray-700 bg-yellow-50 shadow-md p-2 hover:bg-yellow-100 hover:shadow-amber-400/50">
                        {props.text}
                    </IconButton>
            }
        </Link>);
}

export default MediaChips;