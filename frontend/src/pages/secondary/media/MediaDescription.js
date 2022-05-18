import {useCallback, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import MediaDetails from "../../../components/media/MediaDetails";
import MediaService from "../../../services/MediaService";
import Spinner from "../../../components/animation/Spinner";
import GenreService from "../../../services/GenreService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import CommentSection from "../../../components/comments/CommentSection";
import StudioService from "../../../services/StudioService";
import staffService from "../../../services/StaffService";
import ListService from "../../../services/ListService";
import ListsInMedia from "../../../components/media/ListsInMedia";

function MediaDescription() {
    let {id} = useParams();
    const [t] = useTranslation();

    const [mediaData, setMediaData] = useState(undefined);
    const [genreData, setGenreData] = useState(undefined);
    const [studiosData, setStudiosData] = useState(undefined);
    const [crewData, setCrewData] = useState(undefined);
    const [directorData, setDirectorData] = useState(undefined);
    const [listsData, setListsData] = useState(undefined);

    const {setErrorStatusCode} = useErrorStatus();
    const [page] = useState(1);
    const pageSize = 4;

    const getMedia = useCallback(async () => {
        try {
            // fetch
            const mediaDataToAdd = await MediaService.getMedia(id);
            const genreDataToAddPromise = GenreService.getMediaGenres(mediaDataToAdd.genreUrl);
            const studiosDataToAddPromise = StudioService.getMediaStudios(mediaDataToAdd.studiosUrl);
            const directorsDataToAddPromise = staffService.getMediaDirectors(mediaDataToAdd.staffUrl);
            const crewDataToAddPromise = staffService.getMediaCrew(mediaDataToAdd.staffUrl);
            const listsDataToAddPromise = ListService.getMediaLists({url: mediaDataToAdd.listsContainUrl, page: page, pageSize: pageSize})
            // promise all
            const [genreDataToAdd, studiosDataToAdd, directorsDataToAdd, crewDataToAdd, listsDataToAdd] = await Promise.all([genreDataToAddPromise, studiosDataToAddPromise, directorsDataToAddPromise, crewDataToAddPromise, listsDataToAddPromise]);

            // set
            setMediaData(mediaDataToAdd);
            setGenreData(genreDataToAdd);
            setStudiosData(studiosDataToAdd);
            setDirectorData(directorsDataToAdd);
            setCrewData(crewDataToAdd);
            setListsData(listsDataToAdd);
        } catch (e) {
            setErrorStatusCode(e.response.status);
        }
    }, [id, page, setErrorStatusCode]);

    useEffect(
        () => {
            getMedia();
        }, [getMedia, id]
    );

    return (
        <>
            {(mediaData && genreData && studiosData && (directorData || crewData)) ? <>
                <MediaDetails image={mediaData.imageUrl} title={mediaData.title}
                              releaseYear={mediaData.releaseDate.slice(0, 4)} countryName={mediaData.country}
                              description={mediaData.description} mediaData={mediaData}
                              genres={genreData}
                              studios={studiosData}
                              directors={directorData}
                              crew={crewData}
                />
                {
                    // Lists that contain this media.
                    listsData && listsData.data.length > 0 && <ListsInMedia title={t('media_lists')} data={listsData.data}/>
                }
                <CommentSection commentsUrl={mediaData.commentsUrl} type="MEDIA"/>
            </> : <Spinner/>}
        </>
    );
}

export default MediaDescription;