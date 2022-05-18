import mediaApi from '../api/MediaApi'
import {MediaType} from '../enums/MediaType'
import {StaffRole} from '../enums/StaffRole'
import {parsePaginatedResponse} from "./ResponseUtils";

const MediaService = (() => {

    // TODO use sortType enum
    const getMediaList = async ({page, pageSize, mediaType, genres, sortType, decades, query, notInList}) => {
        const res = await mediaApi.getMediaList({page, pageSize, mediaType, genres, sortType, decades, query, notInList});
        return parsePaginatedResponse(res);
    }

    const getFilms = async ({page, pageSize, genres, sortType, decades, query, notInList}) => {
        return await getMediaList({page, pageSize, mediaType: MediaType.FILMS, genres, sortType, decades, query, notInList})
    }

    const getSeries = async ({page, pageSize, genres, sortType, decades, query, notInList}) => {
        return await getMediaList({page, pageSize, mediaType: MediaType.SERIES, genres, sortType, decades, query, notInList})
    }

    const getMedia = async (id) => {
        const res = await mediaApi.getMedia(id);
        return res.data;
    }

    const getMediaByUrl = async ({url, page, pageSize}) => {
        const res = await mediaApi.getMediaByUrl({url, page, pageSize});
        return parsePaginatedResponse(res);
    }

    const getGenreMedia = async ({url, page, pageSize}) => {
        const res = await mediaApi.getGenreMedia({url, page, pageSize});
        return parsePaginatedResponse(res);
    }

    const getMediaFromStaff = async ({url, page, pageSize, staffRole}) => {
        const res = await mediaApi.getMediaFromStaff({url, page, pageSize, staffRole});
        return parsePaginatedResponse(res);
    }

    const getDirectorMedia = async ({url, page, pageSize}) => {
        return await getMediaFromStaff({url, page, pageSize, staffRole: StaffRole.DIRECTOR});
    }

    const getActorMedia = async ({url, page, pageSize}) => {
        return await getMediaFromStaff({url, page, pageSize, staffRole: StaffRole.ACTOR});
    }

    const getStudioMedia = async ({url, page, pageSize}) => {
        const res = await mediaApi.getStudioMedia({url, page, pageSize});
        return parsePaginatedResponse(res);
    }

    return {
        getMediaList,
        getFilms,
        getSeries,
        getMedia,
        getMediaByUrl,
        getGenreMedia,
        getMediaFromStaff: getMediaFromStaff,
        getDirectorMedia,
        getActorMedia,
        getStudioMedia
    }
})();

export default MediaService;