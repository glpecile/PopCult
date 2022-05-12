import {useTranslation} from "react-i18next";
import BrandingImg from "../../../components/login/BrandingImg";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import {Link} from "react-router-dom";

const SuccessfulRegister = () => {
    const {t} = useTranslation();


    return (
        <>
            <BrandingImg/>
            <div className="flex-grow mt-3">
                <div className="w-full max-w-sm mx-auto p-2.5 font-sans rounded-lg shadow-lg bg-white">
                    <h2 className="text-3xl m-3 text-center">
                        {t('welcome_popcult')}
                    </h2>
                    <div className="whitespace-pre-wrap m-3 mb-0">
                        {t('welcome_body')}
                    </div>
                    <Link className="flex justify-end" to='/'>
                        <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded">
                            <HomeOutlinedIcon/> {t('nav_home')}
                        </button>
                    </Link>
                </div>
            </div>
        </>);
}
export default SuccessfulRegister;