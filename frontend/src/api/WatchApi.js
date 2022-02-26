import api from './api'

const watchApi = (() => {

    const getUserWatchedMedia = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/watched-media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isWatchedMedia = ({username, mediaId}) => {
        return api.get(`/users/${username}/watched-media/${mediaId}`);
    }

    const addMediaToWatched = ({username, mediaId, dateTime}) => {
        return api.put(`/users/${username}/watched-media/${mediaId}`,
            {
                'dateTime': dateTime //"dateTime": "2019-12-03T10:15:30"
            });
    }

    const removeMediaFromWatched = ({username, mediaId}) => {
        return api.delete(`/users/${username}/watched-media/${mediaId}`);
    }

    const getUserToWatchMedia = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/to-watch-media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isToWatchMedia = ({username, mediaId}) => {
        return api.get(`/users/${username}/to-watch-media/${mediaId}`);
    }

    const addMediaToWatch = ({username, mediaId}) => {
        return api.put(`/users/${username}/to-watch-media/${mediaId}`);
    }

    const removeMediaFromToWatch = ({username, mediaId}) => {
        return api.delete(`/users/${username}/to-watch-media/${mediaId}`);
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
