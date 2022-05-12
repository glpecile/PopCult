import {useCallback, useEffect, useState} from "react";
import MediaDetails from "../../../components/media/MediaDetails";
import MediaService from "../../../services/MediaService";
import Spinner from "../../../components/animation/Spinner";
import GenreService from "../../../services/GenreService";
import StudioService from "../../../services/StudioService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import {useLocation} from "react-router-dom";

function MediaDescription(props) {
    const location = useLocation();
    const id = location.pathname.split('/')[3];
    const [mediaData, setMediaData] = useState(undefined);
    const [genreData, setGenreData] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();

    const getMedia = useCallback(async () => {
        try {
            const mediaDataToAdd = await MediaService.getMedia(id);
            console.log(mediaDataToAdd);
            const genreDataToAdd = await GenreService.getMediaGenres(mediaDataToAdd.genreUrl);
            console.log(genreDataToAdd);
            // const studiosToAdd = await StudioService.getMediaStudios(mediaDataToAdd.url);
            // console.log(studiosToAdd);
            setMediaData(mediaDataToAdd);
            setGenreData(genreDataToAdd);
        } catch (e) {
            setErrorStatusCode(e.response.status);
        }
    }, [id, setErrorStatusCode]);

    useEffect(
        () => {
            getMedia();
        }, [getMedia, id]
    );

    return (
        <>
            {!mediaData && !genreData &&
                <Spinner/>}
            {mediaData && genreData && <>
                <MediaDetails image={mediaData.imageUrl} title={mediaData.title}
                              releaseYear={mediaData.releaseDate.slice(0, 4)} countryName={mediaData.country}
                              description={mediaData.description} mediaData={mediaData}
                              genres={genreData}
                />
                {/* TODO: Listas que contengan a la media */}
                {/* TODO: Comment Section*/}
            </>}
        </>
    );
}

export default MediaDescription;