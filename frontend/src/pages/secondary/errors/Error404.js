import {Link} from "react-router-dom";

export default function Error404() {
    return (
        <div className="flex-grow whitespace-pre-line">
            <div className="flex flex-wrap p-3.5 mx-auto my-auto">
                <img className="w-80 pt-12" src={require('../../../images/PopCultLogoX.png')} alt="error_image"/>
                <div className="flex flex-col pl-8">
                    <h1 className="text-6xl font-black text-justify">
                        Error 404.
                    </h1>
                    <p className="text-2xl font-semibold text-justify">
                        Page Not Found
                    </p>
                    <Link className="text-2xl font-bold text-purple-500 hover:text-purple-900" to='/'>Go Home.</Link>
                </div>
            </div>
        </div>
    );
}