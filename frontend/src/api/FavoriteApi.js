import api from './api'

const favoriteApi = (() => {

    const getUserFavoriteMedia = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/favorite-media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isFavoriteMedia = ({username, mediaId}) => {
        return api.get(`/users/${username}/favorite-media/${mediaId}`);
    }

    const addMediaToFavorites = ({username, mediaId}) => {
        return api.put(`/users/${username}/favorite-media/${mediaId}`);
    }

    const removeMediaFromFavorites = ({username, mediaId}) => {
        return api.delete(`/users/${username}/favorite-media/${mediaId}`);
    }

    const getUserFavoriteLists = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/favorite-lists`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isFavoriteList = ({username, listId}) => {
        return api.get(`/users/${username}/favorite-lists/${listId}`);
    }

    const addListToFavorites = ({username, listId}) => {
        return api.put(`/users/${username}/favorite-lists/${listId}`);
    }

    const removeListFromFavorites = ({username, listId}) => {
        return api.delete(`/users/${username}/favorite-lists/${listId}`);
    }

    const getUserRecommendedMedia = ({username, page, pageSize, mediaType}) => {
        return api.get(`/users/${username}/recommended-media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    'type': mediaType
                }
            });
    }

    const getUserRecommendedLists = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/recommended-lists`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    return {
        getUserFavoriteMedia,
        isFavoriteMedia,
        addMediaToFavorites,
        removeMediaFromFavorites,
        getUserFavoriteLists,
        isFavoriteList,
        addListToFavorites,
        removeListFromFavorites,
        getUserRecommendedMedia,
        getUserRecommendedLists
    }
})();

export default favoriteApi;