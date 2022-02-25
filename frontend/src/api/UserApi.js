import api from './api'

const userApi = (() => {

    const login = ({username, password}) => {
        return api.post('/authenticate', {username: username, password: password});
    }

    const getUsers = ({page, pageSize, userRole, banned}) => {
        return api.get(`/users`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(userRole && {'role': userRole}),
                    ...(banned && {'banned': banned})
                }
            });
    }

    const getUser = (username) => {
        return api.get(`/users/${username}`);
    }

    const createUser = ({email, username, password, name}) => {
        return api.post(`/users`,
            {
                'email': email,
                'username': username,
                'password': password,
                'name': name
            });
    }

    const deleteUser = (username) => {
        return api.delete(`/users/${username}`);
    }

    const editUser = ({username, name}) => {
        return api.put(`users/${username}`, {'name': name});
    }

    const changePassword = ({username, currentPassword, newPassword}) => {
        console.log(currentPassword + '.' + newPassword);
        return api.put(`/users/${username}/password`,
            {
                'currentPassword': currentPassword,
                'newPassword': newPassword
            });
    }

    const createPasswordResetToken = (email) => {
        return api.post(`/users/reset-password`, {'email': email});
    }

    const resetPassword = ({token, newPassword}) => {
        return api.put(`/users/reset-password`,
            {
                'token': token,
                'newPassword': newPassword
            });
    }

    const sendVerificationToken = (email) => {
        return api.post(`/users/verification`, {'email': email});
    }

    const verifyUser = (token) => {
        return api.put(`/users/verification`, {'token': token})
    }

    const uploadUserImage = ({username, formData}) => {
        return api.put(`/users/${username}/image`, formData);
    }

    const deleteProfileImage = (username) => {
        return api.delete(`/users/${username}/image`);
    }

    const createModRequest = (username) => {
        return api.post(`/users/${username}/mod-requests`);
    }

    const removeMod = (username) => {
        return api.delete(`/users/${username}/mod`);
    }

    const banUser = (username) => {
        return api.put(`/users/${username}/locked`);
    }

    const unbanUser = (username) => {
        return api.delete(`/users/${username}/locked`);
    }

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

    const getUserNotifications = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/notifications`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getUserCollaborationRequests = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/collab-requests`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getUserRecommendedMedia = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/recommended-media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
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
        login,
        getUsers,
        getUser,
        createUser,
        deleteUser,
        editUser,
        changePassword,
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

export default userApi;