import {Link} from "react-router-dom";

function MediaCard(props) {
    let mediaType = props.type;
    if (mediaType.localeCompare("serie") === 0) {
        mediaType = "series";
    }
    return (
        <Link className="text-decoration-none" to={'/media/' + mediaType + '/' + props.id}>
            <div
                className="flex flex-col h-full bg-white rounded-lg shadow-md hover:shadow-indigo-500/50 group transition duration-300 ease-in-out hover:bg-violet-50/50 transform hover:-translate-1 hover:scale-105 active:scale-95">
                <img className="w-full rounded-t-lg" src={props.image} alt="media_image"/>
                <div
                    className="inline-block text-left tracking-tight whitespace-normal text-gray-800 group-hover:text-violet-900 font-medium text-lg p-1.5 m-2.5">
                    <b>{props.title}</b> ({props.releaseDate})
                </div>
            </div>
        </Link>
    );
}

export default MediaCard;