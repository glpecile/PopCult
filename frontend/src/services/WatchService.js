import watchApi from '../api/WatchApi';
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const watchService = (() => {

    const getUserWatchedMedia = async ({username, page, pageSize}) => {
        const res = await watchApi.getUserWatchedMedia({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isWatchedMedia = async ({username, mediaId}) => {
        const res = await watchApi.isWatchedMedia({username, mediaId});
        return res.data;
    }

    const addMediaToWatched = async ({username, mediaId, dateTime}) => {
        await watchApi.addMediaToWatched({username, mediaId, dateTime});
    }

    const removeMediaFromWatched = async ({username, mediaId}) => {
        await watchApi.removeMediaFromWatched({username, mediaId});
    }

    const getUserToWatchMedia = async ({username, page, pageSize}) => {
        const res = await watchApi.getUserToWatchMedia({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isToWatchMedia = async ({username, mediaId}) => {
        const res = await watchApi.isToWatchMedia({username, mediaId});
        return res.data;
    }

    const addMediaToWatch = async ({username, mediaId}) => {
        await watchApi.addMediaToWatch({username, mediaId});
    }

    const removeMediaFromToWatch = async ({username, mediaId}) => {
        await watchApi.removeMediaFromToWatch({username, mediaId});
    }

    return {
        getUserWatchedMedia,
        isWatchedMedia,
        addMediaToWatched,
        removeMediaFromWatched,
        getUserToWatchMedia,
        isToWatchMedia,
        addMediaToWatch,
        removeMediaFromToWatch
    }
})();

export default watchService;