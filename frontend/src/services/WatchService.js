import watchApi from '../api/WatchApi';
import {parsePaginatedResponse} from "./ResponseUtils";

const watchService = (() => {

    const getUserWatchedMedia = async ({url, page, pageSize}) => {
        const res = await watchApi.getUserWatchedMedia({url, page, pageSize})
        return parsePaginatedResponse(res);
    }

    const isWatchedMedia = async (url) => {
        const res = await watchApi.isWatchedMedia(url);
        return res.data;
    }

    const addMediaToWatched = async ({url, dateTime}) => {
        await watchApi.addMediaToWatched({url, dateTime});
    }

    const removeMediaFromWatched = async (url) => {
        await watchApi.removeMediaFromWatched(url);
    }

    const getUserToWatchMedia = async ({url, page, pageSize}) => {
        const res = await watchApi.getUserToWatchMedia({url, page, pageSize})
        return parsePaginatedResponse(res);
    }

    const isToWatchMedia = async (url) => {
        const res = await watchApi.isToWatchMedia(url);
        return res.data;
    }

    const addMediaToWatch = async (url) => {
        await watchApi.addMediaToWatch(url);
    }

    const removeMediaFromToWatch = async (url) => {
        await watchApi.removeMediaFromToWatch(url);
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