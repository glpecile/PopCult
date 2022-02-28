const IconCard = (props) => {
    return (<div onClick={props.onClick}
        className="group flex flex-col bg-white items-center shadow-md hover:shadow-indigo-500/50 rounded-lg p-3 transition duration-300 ease-in-out hover:bg-purple-50 transform hover:-translate-y-1 hover:scale-110">
        {props.icon}
        <h2 className="text-2xl text-center py-2 pb-2.5">
            <div className="stretched-link text-purple-500 group-hover:text-purple-900">
                <b>
                    {props.title}
                </b>
            </div>
        </h2>
        <p className="p-2.5 m-1.5 text-center">
            {props.description}
        </p>
    </div>);
}
export default IconCard;