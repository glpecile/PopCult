import CloseIcon from '@mui/icons-material/Close';

const CompactMediaCard = (props) => {
    return (
        <div
            className={"w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-300 ease-in-out hover:shadow-indigo-500/50 hover:bg-violet-50/50 " + props.className}>
            <div className="flex items-center">
                <img className="object-cover w-15 h-20" src={props.image} alt="media_image"/>
                <h4 className="pl-3 py-4 text-xl font-semibold tracking-tight text-gray-800">
                    {props.title + " "}
                    (
                    {props.releaseDate}
                    )
                </h4>
            </div>
            {(props.canDelete) && <button onClick={props.deleteMedia}>
                <CloseIcon className="flex justify-end mr-1 hover:text-red-400"></CloseIcon>
            </button>}
        </div>
    );
}
export default CompactMediaCard;