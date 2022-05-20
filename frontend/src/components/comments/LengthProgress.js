import {CircularProgress} from "@mui/material";

const LengthProgress = (props) => {
    return (
        <div className="flex justify-end">
            <p className={(props.length > props.max ? "text-red-400 " : " ") + "text-xs pr-1"}>
                {props.text}
            </p>
            <CircularProgress variant="determinate" size="1rem" value={props.length / props.max * 100} />
        </div>
    )
}

export default LengthProgress;