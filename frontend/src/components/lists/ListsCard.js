import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import useErrorStatus from "../../hooks/useErrorStatus";
import listService from "../../services/ListService";
import Spinner from "../animation/Spinner";

function ListsCard(content) {
    const [media, setMedia] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getMediaPictures() {
            try {
                const data = await listService.getMediaInList({url: content.mediaUrl, pageSize: 4});
                setMedia(data.data);
                //TODO if data.len < 4, completar con el png vacio.
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }
        getMediaPictures();
    }, [content.mediaUrl, setErrorStatusCode]);

    return (<>{media ? <div
            className="flex flex-col h-full bg-white rounded-lg shadow-md hover:shadow-indigo-500/50 group transition duration-300 ease-in-out hover:bg-violet-50/25 transform hover:-translate-1 hover:scale-105 active:scale-95">
            <Link className='text-decoration-none' to={'/lists/' + content.id}>
                <div className="row row-cols-2 mx-0 px-0">
                    {media.map((data) => {
                        return <div className="col px-0 mx-0" key={data.id}><img className="img-fluid rounded-tl-lg"
                                                                                 src={data.imageUrl}
                                                                                 alt={data.id}/>
                        </div>;
                    })}
                </div>
                <div
                    className="stretched-link overflow-ellipsis text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-violet-900 font-medium text-lg p-1.5 m-2.5">
                    <b>{content.listTitle}</b>
                </div>
            </Link>
        </div> :<Spinner/>}</>
    );
}

export default ListsCard;