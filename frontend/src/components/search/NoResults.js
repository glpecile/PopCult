const NoResults = (props) => {
    return (
        <div className="flex flex-col justify-center items-center space-y-3 my-3 text-gray-400">
            <img className="w-36" alt={props.title} src={require('../../images/PopCultLogoX.webp')}/>
            <span>{props.title}</span>
        </div>
    )
}

export default NoResults;