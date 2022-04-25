import CloseIcon from '@mui/icons-material/Close';

const CompactMediaCard = (props) => {
    return (
        <div
            className={"w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107 " + props.className}>
            <div className="flex">
                <img className="object-cover w-15 h-20" src={props.image} alt="media_image"/>
                <h4 className="pl-3 py-4 text-xl font-semibold tracking-tight text-gray-800">
                    {props.title}
                    (
                    {props.releaseDate}
                    )
                </h4>
            </div>
            {(props.canDelete || true) && <button onClick={props.deleteMedia}>
                <CloseIcon className="flex justify-end hover:text-red-400"></CloseIcon>
            </button>}
        </div>
    );
}
export default CompactMediaCard;