import {Link} from "react-router-dom";

function ListsCard(content) {
    return (<div
            className="flex flex-col h-full bg-white rounded-lg shadow-md hover:shadow-indigo-500/50 group transition duration-300 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-105">
            <Link className='text-decoration-none' to={'/lists/' + content.id}>
            <div className="row row-cols-2 mx-0 px-0">
                <div className="col px-0 mx-0"><img className="img-fluid rounded-tl-lg" src={content.image1} alt=""/>
                </div>
                <div className="col px-0 mx-0"><img className="img-fluid rounded-tr-lg" src={content.image2} alt=""/>
                </div>
                <div className="col px-0 mx-0"><img className="img-fluid" src={content.image3} alt=""/>
                </div>
                <div className="col px-0 mx-0"><img className="img-fluid" src={content.image4} alt=""/>
                </div>
            </div>
            <div
                className="stretched-link overflow-ellipsis text-left tracking-tight align-text-top whitespace-normal text-gray-800 group-hover:text-purple-900 font-medium text-lg p-1.5 m-2.5">
                <b>{content.listTitle}</b>
            </div>
            </Link>
        </div>
    );
}

export default ListsCard;