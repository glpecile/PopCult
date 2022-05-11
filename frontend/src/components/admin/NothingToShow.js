const NothingToShow = (props) => {
    return (<div className="flex-col flex-wrap p-4 space-x-4">
        <img className="w-36 object-center mx-auto" src={require("../../images/PopCultLogoExclamation.png")}
             alt="no_results_image"/>
        <h3 className="text-xl text-gray-400 py-2 mt-3 text-center">
            {props.text}
        </h3>
    </div>);
}
export default NothingToShow;