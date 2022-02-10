import {Link} from "react-router-dom";

function LoginWrapper(props) {
    return (
        <div className="min-h-screen flex flex-col">
            <div className="flex-grow space-y-2">
                {/*Logo and card title*/}
                <Link className="flex justify-center items-center pt-16" to='/'>
                    <img className="w-32" src={require("../../images/PopCultLogo.png")} alt="popcult_logo"/>
                </Link>
                <h2 className="font-bold text-4xl text-center text-white py-2">Log In</h2>
                <div className="w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
                    {props.children}
                </div>
            </div>
        </div>
    );
}

export default LoginWrapper;