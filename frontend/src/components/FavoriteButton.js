import {IconButton, Tooltip} from "@mui/material";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import {useTranslation} from "react-i18next";

const FavoriteButton = (props) => {
    const [t] = useTranslation();

    let buttonStyle = "group h-min w-min"
    let iconStyle = "text-violet-500 group-hover:text-violet-900 transition duration-300 ease-in-out transform group-active:scale-90"

    return <Tooltip title={props.isLiked ? t('media_details_remove_liked') : t('media_details_add_liked')} arrow>
        <IconButton onClick={props.handleLike} className={buttonStyle}>
            {
                props.isLiked ? <FavoriteIcon fontSize="large" className={iconStyle}/> :
                    <FavoriteBorderOutlinedIcon fontSize="large" className={iconStyle}/>
            }
        </IconButton>
    </Tooltip>;
}
export default FavoriteButton;