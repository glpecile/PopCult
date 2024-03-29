import favoriteApi from '../api/FavoriteApi'
import {MediaType} from '../enums/MediaType'
import {parsePaginatedResponse} from "./ResponseUtils";

const favoriteService = (() => {

    const getUserFavoriteMedia = async ({url, page, pageSize}) => {
        const res = await favoriteApi.getUserFavoriteMedia({url, page, pageSize})
        return parsePaginatedResponse(res);
    }

    const isFavoriteMedia = async (url) => {
        const res = await favoriteApi.isFavoriteMedia(url);
        return res.data;
    }

    const addMediaToFavorites = async (url) => {
        await favoriteApi.addMediaToFavorites(url);
    }

    const removeMediaFromFavorites = async (url) => {
        await favoriteApi.removeMediaFromFavorites(url);
    }

    const getUserFavoriteLists = async ({url, page, pageSize}) => {
        const res = await favoriteApi.getUserFavoriteLists({url, page, pageSize})
        return parsePaginatedResponse(res);
    }

    const isFavoriteList = async (url) => {
        const res = await favoriteApi.isFavoriteList(url);
        return res.data;
    }

    const addListToFavorites = async (url) => {
        await favoriteApi.addListToFavorites(url);
    }

    const removeListFromFavorites = async (url) => {
        await favoriteApi.removeListFromFavorites(url);
    }

    const getUserRecommendedMedia = async ({url, page, pageSize, mediaType}) => {
        const res = await favoriteApi.getUserRecommendedMedia({url, page, pageSize, mediaType})
        return parsePaginatedResponse(res);
    }

    const getUserRecommendedFilms = async ({url, page, pageSize}) => {
        return await getUserRecommendedMedia({url, page, pageSize, mediaType: MediaType.FILMS})
    }

    const getUserRecommendedSeries = async ({url, page, pageSize}) => {
        return await getUserRecommendedMedia({url, page, pageSize, mediaType: MediaType.SERIES})
    }

    const getUserRecommendedLists = async ({url, page, pageSize}) => {
        const res = await favoriteApi.getUserRecommendedLists({url, page, pageSize});
        return parsePaginatedResponse(res);
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
        getUserRecommendedFilms,
        getUserRecommendedSeries,
        getUserRecommendedLists
    }
})();

export default favoriteService;
