import {Link} from "react-router-dom";

function MediaCard(props) {
    return (
        <div className="flex flex-col h-full bg-white rounded-lg shadow-md group transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-105">
            <Link className="text-decoration-none" to='/'>
                <img className="img-fluid rounded-t-lg" src={props.image} alt="media_image"/>
                <div
                    className="stretched-link overflow-ellipsis text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-purple-900 font-medium text-lg p-1.5 m-2.5">
                    <b>{props.title}</b> ({props.releaseDate})
                </div>
            </Link>
        </div>
    );
}

export default MediaCard;