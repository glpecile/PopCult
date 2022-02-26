import {useState} from "react";
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffOutlinedIcon from '@mui/icons-material/VisibilityOffOutlined';
import EventBusyIcon from '@mui/icons-material/EventBusy';
import EventAvailableOutlinedIcon from '@mui/icons-material/EventAvailableOutlined';
import {IconButton, Tooltip} from "@mui/material";
import {useTranslation} from "react-i18next";

const MediaOptions = () => {
    const [isLiked, setLiked] = useState(false);
    const [isWatched, setWatched] = useState(false);
    const [isInWatchlist, setInWatchlist] = useState(false);
    const [t] = useTranslation();
    let buttonStyle = "group"
    let iconStyle = "text-purple-500 group-hover:text-purple-900 transition duration-300 ease-in-out transform group-active:scale-90"

    const handleLike = () => {
        setLiked(!isLiked);
    }

    const handleWatched = () => {
        setWatched(!isWatched);
    }

    const handleInWatchlist = () => {
        setInWatchlist(!isInWatchlist);
    }

    return (
        <div className="flex justify-around py-2">
            <Tooltip title={isLiked ? t('media_details_remove_liked') : t('media_details_add_liked')} arrow>
                <IconButton onClick={handleLike} className={buttonStyle}>
                    {
                        isLiked ? <FavoriteIcon className={iconStyle}/> : <FavoriteBorderOutlinedIcon className={iconStyle}/>
                    }
                </IconButton>
            </Tooltip>
            <Tooltip title={isWatched ? t('media_details_add_watched') : t('media_details_remove_liked')} arrow>
                <IconButton onClick={handleWatched} className={buttonStyle}>
                    {
                        isWatched ? <VisibilityIcon className={iconStyle}/> : <VisibilityOffOutlinedIcon className={iconStyle}/>
                    }
                </IconButton>
            </Tooltip>
            <Tooltip title={isInWatchlist ? t('media_details_add_watchlist') : t('media_details_remove_watchlist')} arrow>
                <IconButton onClick={handleInWatchlist} className={buttonStyle}>
                    {
                        isInWatchlist ? <EventBusyIcon className={iconStyle}/> : <EventAvailableOutlinedIcon className={iconStyle}/>
                    }
                </IconButton>
            </Tooltip>
        </div>
    )
}

export default MediaOptions;