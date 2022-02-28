import favoriteApi from '../api/FavoriteApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {MediaType} from '../enums/MediaType'

const favoriteService = (() => {

    const getUserFavoriteMedia = async ({username, page, pageSize}) => {
        const res = await favoriteApi.getUserFavoriteMedia({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isFavoriteMedia = async ({username, mediaId}) => {
        const res = await favoriteApi.isFavoriteMedia({username, mediaId});
        return res.data;
    }

    const addMediaToFavorites = async ({username, mediaId}) => {
        await favoriteApi.addMediaToFavorites({username, mediaId});
    }

    const removeMediaFromFavorites = async ({username, mediaId}) => {
        await favoriteApi.removeMediaFromFavorites({username, mediaId});
    }

    const getUserFavoriteLists = async ({username, page, pageSize}) => {
        const res = await favoriteApi.getUserFavoriteLists({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isFavoriteList = async ({username, listId}) => {
        const res = await favoriteApi.isFavoriteList({username, listId});
        return res.data;
    }

    const addListToFavorites = async ({username, listId}) => {
        await favoriteApi.addListToFavorites({username, listId});
    }

    const removeListFromFavorites = async ({username, listId}) => {
        await favoriteApi.removeListFromFavorites({username, listId});
    }

    const getUserRecommendedMedia = async ({username, page, pageSize, mediaType}) => {
        const res = await favoriteApi.getUserRecommendedMedia({username, page, pageSize, mediaType})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getUserRecommendedFilms = async ({username, page, pageSize}) => {
        const mediaType = MediaType.FILMS;
        return await getUserRecommendedMedia({username, page, pageSize, mediaType})
    }

    const getUserRecommendedSeries = async ({username, page, pageSize}) => {
        const mediaType = MediaType.SERIES;
        return await getUserRecommendedMedia({username, page, pageSize, mediaType})
    }

    const getUserRecommendedLists = async ({username, page, pageSize}) => {
        const res = await favoriteApi.getUserRecommendedLists({username, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
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
