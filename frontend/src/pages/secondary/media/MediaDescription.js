import {useCallback, useEffect, useState} from "react";
import MediaDetails from "../../../components/media/MediaDetails";
import MediaService from "../../../services/MediaService";
import Spinner from "../../../components/animation/Spinner";

function MediaDescription() {
    const id = window.location.pathname.split('/')[3];
    const [mediaData, setMediaData] = useState(undefined);

    const getMedia = useCallback(async () => {
        try {
            const mediaDataToAdd = await MediaService.getMedia(id);
            console.log(mediaDataToAdd);
            setMediaData(mediaDataToAdd);
        } catch (e) {
            console.log(e);
        }
    }, [id]);

    useEffect(
        () => {
            getMedia();
        }, [getMedia]
    );

    return (
        <>
            {!mediaData &&
                <Spinner/>
            }
            {mediaData && <>
                <MediaDetails image={mediaData.imageUrl} title={mediaData.title}
                              releaseYear={mediaData.releaseDate.slice(0, 4)} countryName={mediaData.country}
                              description={mediaData.description}/>
                {/* TODO: Listas que contengan a la media */}
                {/* TODO: Comment Section*/}
            </>}
        </>
    );
}

export default MediaDescription;