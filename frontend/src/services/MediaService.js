import mediaApi from '../api/MediaApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {MediaType} from '../enums/MediaType'

const MediaService = (() => {

    const getMediaList = async ({page, pageSize, mediaType, genres, sortType, decades, query}) => {
        const res = await mediaApi.getMediaList({page, pageSize, mediaType, genres, sortType, decades, query});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getFilms = async ({page, pageSize, genres, sortType, decades, query}) => {
        const mediaType = MediaType.FILMS;
        return await getMediaList({page, pageSize, mediaType, genres, sortType, decades, query})
    }

    const getSeries = async ({page, pageSize, genres, sortType, decades, query}) => {
        const mediaType = MediaType.SERIES;
        return await getMediaList({page, pageSize, mediaType, genres, sortType, decades, query})
    }

    const getMedia = async (id) => {
        const res = await mediaApi.getMedia(id);
        return res.data;
    }

    return {
        getMediaList,
        getFilms,
        getSeries,
        getMedia
    }
})();

export default MediaService;