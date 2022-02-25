import userApi from '../api/UserApi'
import parse from 'parse-link-header'

const UserService = (function () {

    const login = async ({username, password}) => {
        const res = await userApi.login({username, password});
        return res.headers.authorization.split(' ')[1];
    }

    const getUsers = async ({page, pageSize, userRole, banned}) => {
        const res = await userApi.getUsers({page, pageSize, userRole, banned});
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data}
    }

    const getUser = async (username) => {
        const res = await userApi.getUser(username);
        return res.data;
    }

    const createUser = async ({email, username, password, name}) => {
        await userApi.createUser({email, username, password, name});
        //el response retorna el header location con la url del usuario nuevo. Se puede llegar a usar
    }

    const deleteUser = async (username) => {
        await userApi.deleteUser(username);
    }

    const editUser = async ({username, name}) => {
        await userApi.editUser({username, name});
    }

    const changeUserPassword = async ({username, currentPassword, newPassword}) => {
        await userApi.changePassword({username, currentPassword, newPassword})
    }

    const createPasswordResetToken = async (email) => {
        await userApi.createPasswordResetToken(email);
        //Retorna tanto location del reset-password como una entidad con el token
    }

    const resetPassword = async ({token, newPassword}) => {
        await userApi.resetPassword({token, newPassword});
    }

    const sendVerificationToken = async (email) => {
        const res = await userApi.sendVerificationToken(email);
        return res.headers.authorization.split(' ')[1];
    }

    const verifyUser = async ({token, newPassword}) => {
        await userApi.verifyUser({token, newPassword});
    }

    const uploadUserImage = async ({username, formData}) => {
        await userApi.uploadUserImage({username: username, formData: formData});
    }

    const deleteProfileImage = async (username) => {
        await userApi.deleteProfileImage(username);
    }

    const createModRequest = async (username) => {
        await userApi.createModRequest(username);
    }

    const removeMod = async (username) => {
        await userApi.removeMod(username);
    }

    const banUser = async (username) => {
        await userApi.banUser(username);
    }

    const unbanUser = async (username) => {
        await userApi.unbanUser(username);
    }

    const getUserFavoriteMedia = async ({username, page, pageSize}) => {
        const res = await userApi.getUserFavoriteMedia({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isFavoriteMedia = async ({username, mediaId}) => {
        const res = await userApi.isFavoriteMedia({username, mediaId});
        return res.data;
    }

    const addMediaToFavorites = async ({username, mediaId}) => {
        await userApi.addMediaToFavorites({username, mediaId});
    }

    const removeMediaFromFavorites = async ({username, mediaId}) => {
        await userApi.removeMediaFromFavorites({username, mediaId});
    }

    const getUserWatchedMedia = async ({username, page, pageSize}) => {
        const res = await userApi.getUserWatchedMedia({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isWatchedMedia = async ({username, mediaId}) => {
        const res = await userApi.isWatchedMedia({username, mediaId});
        return res.data;
    }

    const addMediaToWatched = async ({username, mediaId, dateTime}) => {
        await userApi.addMediaToWatched({username, mediaId, dateTime});
    }

    const removeMediaFromWatched = async ({username, mediaId}) => {
        await userApi.removeMediaFromWatched({username, mediaId});
    }

    const getUserToWatchMedia = async ({username, page, pageSize}) => {
        const res = await userApi.getUserToWatchMedia({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isToWatchMedia = async ({username, mediaId}) => {
        const res = await userApi.isToWatchMedia({username, mediaId});
        return res.data;
    }

    const addMediaToWatch = async ({username, mediaId}) => {
        await userApi.addMediaToWatch({username, mediaId});
    }

    const removeMediaFromToWatch = async ({username, mediaId}) => {
        await userApi.removeMediaFromToWatch({username, mediaId});
    }

    const getUserFavoriteLists = async ({username, page, pageSize}) => {
        const res = await userApi.getUserFavoriteLists({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isFavoriteList = async ({username, listId}) => {
        const res = await userApi.isFavoriteList({username, listId});
        return res.data;
    }

    const addListToFavorites = async ({username, listId}) => {
        await userApi.addListToFavorites({username, listId});
    }

    const removeListFromFavorites = async ({username, listId}) => {
        await userApi.removeListFromFavorites({username, listId});
    }

    const getUserNotifications = async ({username, page, pageSize}) => {
        const res = await userApi.getUserNotifications({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getUserCollaborationRequests = async ({username, page, pageSize}) => {
        const res = await userApi.getUserCollaborationRequests({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getUserRecommendedMedia = async ({username, page, pageSize}) => {
        const res = await userApi.getUserRecommendedMedia({username, page, pageSize})
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getUserRecommendedLists = async ({username, page, pageSize}) => {
        const res = await userApi.getUserRecommendedLists({username, page, pageSize});
        const links = parse(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    return {
        login,
        getUsers,
        getUser,
        createUser,
        deleteUser,
        editUser,
        changeUserPassword,
        createPasswordResetToken,
        resetPassword,
        sendVerificationToken,
        verifyUser,
        uploadUserImage,
        deleteProfileImage,
        createModRequest,
        removeMod,
        banUser,
        unbanUser,
        getUserFavoriteMedia,
        isFavoriteMedia,
        addMediaToFavorites,
        removeMediaFromFavorites,
        getUserWatchedMedia,
        isWatchedMedia,
        addMediaToWatched,
        removeMediaFromWatched,
        getUserToWatchMedia,
        isToWatchMedia,
        addMediaToWatch,
        removeMediaFromToWatch,
        getUserFavoriteLists,
        isFavoriteList,
        addListToFavorites,
        removeListFromFavorites,
        getUserNotifications,
        getUserCollaborationRequests,
        getUserRecommendedMedia,
        getUserRecommendedLists
    };

})();

export default UserService;