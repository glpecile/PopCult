import api from './api'

const favoriteApi = (() => {

    const getUserFavoriteMedia = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isFavoriteMedia = (url) => {
        return api.get(url);
    }

    const addMediaToFavorites = (url) => {
        return api.put(url);
    }

    const removeMediaFromFavorites = (url) => {
        return api.delete(url);
    }

    const getUserFavoriteLists = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isFavoriteList = (url) => {
        return api.get(url);
    }

    const addListToFavorites = (url) => {
        return api.put(url);
    }

    const removeListFromFavorites = (url) => {
        return api.delete(url);
    }

    const getUserRecommendedMedia = ({url, page, pageSize, mediaType}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    'type': mediaType
                }
            });
    }

    const getUserRecommendedLists = ({url, page, pageSize}) => {
        return api.get(url,
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