import favoriteApi from '../api/FavoriteApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {MediaType} from '../enums/MediaType'

const favoriteService = (() => {

    const getUserFavoriteMedia = async ({url, page, pageSize}) => {
        const res = await favoriteApi.getUserFavoriteMedia({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
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
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
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
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getUserRecommendedFilms = async ({url, page, pageSize}) => {
        return await getUserRecommendedMedia({url, page, pageSize, mediaType: MediaType.FILMS})
    }

    const getUserRecommendedSeries = async ({url, page, pageSize}) => {
        return await getUserRecommendedMedia({url, page, pageSize, mediaType: MediaType.SERIES})
    }

    const getUserRecommendedLists = async ({url, page, pageSize}) => {
        const res = await favoriteApi.getUserRecommendedLists({url, page, pageSize});
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
