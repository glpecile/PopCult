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

    const deleteUser = (url) => {
        return api.delete(url);
    }

    const editUser = ({url, name}) => {
        return api.put(url, {'name': name});
    }

    const changePassword = ({url, currentPassword, newPassword}) => {
        console.log(currentPassword + '.' + newPassword);
        return api.put(url,
            {
                'currentPassword': currentPassword,
                'newPassword': newPassword
            });
    }

    const createPasswordResetToken = (email) => {
        return api.post(`/users/password-token`, {'email': email});
    }

    const resetPassword = ({token, newPassword}) => {
        return api.put(`/users/password-token/${token}`,
            {
                'newPassword': newPassword
            });
    }

    const sendVerificationToken = (email) => {
        return api.post(`/users/verification-token`, {'email': email});
    }

    const verifyUser = (token) => {
        return api.put(`/users/verification-token/${token}`);
    }

    const uploadUserImage = ({url, formData}) => {
        return api.put(url, formData);
    }

    const deleteProfileImage = (url) => {
        return api.delete(url);
    }

    const createModRequest = (url) => {
        return api.post(url);
    }

    const removeMod = (url) => {
        return api.delete(url);
    }

    const banUser = (url) => {
        return api.put(url);
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