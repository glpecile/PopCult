import api from './api'

const watchApi = (() => {

    const getUserWatchedMedia = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isWatchedMedia = (url) => {
        return api.get(url);
    }

    const addMediaToWatched = ({url, dateTime}) => {
        return api.put(url,
            {
                'dateTime': dateTime //"dateTime": "2019-12-03T10:15:30"
            });
    }

    const removeMediaFromWatched = (url) => {
        return api.delete(url);
    }

    const getUserToWatchMedia = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isToWatchMedia = (url) => {
        return api.get(url);
    }

    const addMediaToWatch = (url) => {
        return api.put(url);
    }

    const removeMediaFromToWatch = (url) => {
        return api.delete(url);
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

export default watchApi;
