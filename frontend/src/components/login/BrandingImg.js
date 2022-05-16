import {Link} from "react-router-dom";

const closeEye = (e) => {
  e.target.setAttribute('src', "../../images/PopCultLogoClosed.webp");
}

const BrandingImg = () => {
    return (
        <Link className="flex justify-center items-center pt-4" to='/'>
            <img className="w-32 transition duration-300 ease-in-out transform hover:-translate-1 hover:scale-105 active:scale-95"
                 id="popcult_img_logo"
                 src={require("../../images/PopCultLogo.webp")}
                 alt="popcult_logo"
                 onClick={closeEye}
            />
        </Link>
    )
}

export default BrandingImg;