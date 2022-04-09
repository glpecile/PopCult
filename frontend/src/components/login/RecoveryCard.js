import BrandingImg from "./BrandingImg";
import {Helmet} from "react-helmet-async";

const RecoveryCard = (props) => {
    return (
        <>
            <Helmet>
                <title>{props.title}</title>
            </Helmet>
            <div className="flex-grow space-y-2">
                {/* Logo & Card Title */}
                <BrandingImg/>
                <h2 className="font-bold text-4xl text-center text-white py-2">
                    {props.heading}
                </h2>
                <div className="w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
                    {props.children}
                </div>
            </div>
        </>
    );
}

export default RecoveryCard;