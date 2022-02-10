import {Link} from "react-router-dom";

function MediaCard(props) {
    return (
        <div
            className="flex flex-col bg-white rounded-lg shadow-md hover:shadow-indigo-500/50 group transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-105">
            <Link className="text-decoration-none" to={'/media/series/' + props.id}>
                {/*TODO poner si es a /movies o /series cuando tengamos la api*/}
                <img className="img-fluid rounded-t-lg" src={props.image} alt="media_image"/>
                <div
                    className="stretched-link inline-block overflow-ellipsis text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-purple-900 font-medium text-lg p-1.5 m-2.5">
                    <b>{props.title}</b> ({props.releaseDate})
                </div>
            </Link>
        </div>
    );
}

export default MediaCard;