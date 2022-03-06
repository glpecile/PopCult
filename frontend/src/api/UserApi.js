import api from './api'

const userApi = (() => {

    const login = ({username, password}) => {
        const credentials = btoa(`${username}:${password}`)
        return api.get('/users',
            {
                headers: {
                    'Authorization': `Basic ${credentials}`
                }
            });
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

    const removeMod = (url) => {
        return api.delete(url);
    }

    const banUser = (username) => {
        return api.put(`/users/${username}/locked`);
    }

    const unbanUser = (url) => {
        return api.delete(url);
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
        unbanUser
    };

})();

export default userApi;