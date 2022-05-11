import {Twitter} from "@mui/icons-material";
import {IconButton} from "@mui/material";

const TwitterIcon = (props) => {
    return (
        <div className="flex flex-col justify-center items-center">
            <IconButton onClick={ () => {
                window.open("https://twitter.com/share?url=" + props.url + "&text=" + document.title, '_blank');
            }}
                className="rounded-full shadow-md bg-blue-400 w-20 h-20 flex items-center justify-center transition duration-300 ease-in-out transform hover:-translate-1 hover:scale-105 hover:shadow-blue-400/50 active:scale-90">
                <Twitter fontSize="large" className="text-white"/>
            </IconButton>
            <p className="text-center py-2">Tweet</p>
        </div>
    )
}

export default TwitterIcon;