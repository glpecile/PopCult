import mediaApi from '../api/MediaApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {MediaType} from '../enums/MediaType'
import {StaffRole} from '../enums/StaffRole'

const MediaService = (() => {

    const getMediaList = async ({page, pageSize, mediaType, genres, sortType, decades, query}) => {
        const res = await mediaApi.getMediaList({page, pageSize, mediaType, genres, sortType, decades, query});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getFilms = async ({page, pageSize, genres, sortType, decades, query}) => {
        return await getMediaList({page, pageSize, mediaType: MediaType.FILMS, genres, sortType, decades, query})
    }

    const getSeries = async ({page, pageSize, genres, sortType, decades, query}) => {
        const mediaType = MediaType.SERIES;
        return await getMediaList({page, pageSize, mediaType: MediaType.SERIES, genres, sortType, decades, query})
    }

    const getMedia = async (id) => {
        const res = await mediaApi.getMedia(id);
        return res.data;
    }

    const getGenreMedia = async ({url, page, pageSize}) => {
        const res = await mediaApi.getGenreMedia({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getStaffMedia = async ({url, page, pageSize, staffRole}) => {
        const res = await mediaApi.getGenreMedia({url, page, pageSize, staffRole});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getDirectorMedia = async ({url, page, pageSize}) => {
        return await getStaffMedia({url, page, pageSize, staffRole: StaffRole.DIRECTOR});
    }

    const getActorMedia = async ({url, page, pageSize}) => {
        return await getStaffMedia({url, page, pageSize, staffRole: StaffRole.ACTOR});
    }

    const getStudioMedia = async ({url, page, pageSize}) => {
        const res = await mediaApi.getStudioMedia({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    return {
        getMediaList,
        getFilms,
        getSeries,
        getMedia,
        getGenreMedia,
        getStaffMedia,
        getDirectorMedia,
        getActorMedia,
        getStudioMedia
    }
})();

export default MediaService;