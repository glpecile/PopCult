import {useEffect, useState} from "react";
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderOutlinedIcon from '@mui/icons-material/FavoriteBorderOutlined';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffOutlinedIcon from '@mui/icons-material/VisibilityOffOutlined';
import EventBusyIcon from '@mui/icons-material/EventBusy';
import EventAvailableOutlinedIcon from '@mui/icons-material/EventAvailableOutlined';
import {IconButton, Tooltip} from "@mui/material";
import {useTranslation} from "react-i18next";
import useErrorStatus from "../../hooks/useErrorStatus";
import favoriteService from "../../services/FavoriteService";
import watchService from "../../services/WatchService";

const MediaOptions = (props) => {
    const [isLiked, setLiked] = useState(false);
    const [isWatched, setWatched] = useState(false);
    const [isInWatchlist, setInWatchlist] = useState(false);
    const [t] = useTranslation();
    let buttonStyle = "group"
    let iconStyle = "text-purple-500 group-hover:text-purple-900 transition duration-300 ease-in-out transform group-active:scale-90"
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getState() {
            try {
                await favoriteService.isFavoriteMedia(props.mediaData.favoriteUrl);
                setLiked(true);
            } catch (error) {
                setLiked(false);
            }
            try {
                await watchService.isToWatchMedia(props.mediaData.toWatchMediaUrl);
                setInWatchlist(true);
            } catch (error) {
                setInWatchlist(false);
            }
            try {
                await watchService.isWatchedMedia(props.mediaData.watchedMediaUrl);
                setWatched(true);
            } catch (error) {
                setWatched(false);
            }
        }

        getState();
    }, [props.mediaData]);

    const handleLike = () => {
        async function handle() {
            try {
                if (!isLiked) {
                    await favoriteService.addMediaToFavorites(props.mediaData.favoriteUrl);
                }else {
                    await favoriteService.removeMediaFromFavorites(props.mediaData.favoriteUrl)
                }
                setLiked(!isLiked);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        handle();
    }

    const handleWatched = () => {
        async function handle() {
            try {
                if (!isWatched) {
                    await watchService.addMediaToWatched({url: props.mediaData.watchedMediaUrl, dateTime: Date.now()});
                }else{
                    await watchService.removeMediaFromWatched(props.mediaData.watchedMediaUrl)
                }
                setWatched(!isWatched);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        handle();
    }

    const handleInWatchlist = () => {
        async function handle() {
            try {
                if (!isInWatchlist){
                    await watchService.addMediaToWatch(props.mediaData.toWatchMediaUrl);
                }else{
                    await watchService.removeMediaFromToWatch(props.mediaData.toWatchMediaUrl)
                }
                setInWatchlist(!isInWatchlist);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        handle();
    }

    return (
        <div className="flex justify-around py-2">
            <Tooltip title={isLiked ? t('media_details_remove_liked') : t('media_details_add_liked')} arrow>
                <IconButton onClick={handleLike} className={buttonStyle}>
                    {
                        isLiked ? <FavoriteIcon fontSize="large" className={iconStyle}/> :
                            <FavoriteBorderOutlinedIcon fontSize="large" className={iconStyle}/>
                    }
                </IconButton>
            </Tooltip>
            <Tooltip title={isWatched ? t('media_details_add_watched') : t('media_details_remove_watched')} arrow>
                <IconButton onClick={handleWatched} className={buttonStyle}>
                    {
                        isWatched ? <VisibilityIcon fontSize="large" className={iconStyle}/> :
                            <VisibilityOffOutlinedIcon fontSize="large" className={iconStyle}/>
                    }
                </IconButton>
            </Tooltip>
            <Tooltip title={isInWatchlist ? t('media_details_add_watchlist') : t('media_details_remove_watchlist')}
                     arrow>
                <IconButton onClick={handleInWatchlist} className={buttonStyle}>
                    {
                        isInWatchlist ? <EventBusyIcon fontSize="large" className={iconStyle}/> :
                            <EventAvailableOutlinedIcon fontSize="large" className={iconStyle}/>
                    }
                </IconButton>
            </Tooltip>
        </div>
    )
}

export default MediaOptions;