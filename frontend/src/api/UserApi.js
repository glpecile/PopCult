import api from './api'
import {VndType} from '../enums/VndType';

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

    const getUsers = ({page, pageSize, userRole, banned, query, notCollabInList}) => {
        return api.get(`/users`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(userRole && {'role': userRole}),
                    ...(banned && {'banned': banned}),
                    ...(query && {'query': query}),
                    ...(notCollabInList && {'not-collab-in-list': notCollabInList})
                }
            });
    }

    const getUser = ({url}) => {
        return api.get(url);
    }

    const getUserByUsername = (username) => {
        return api.get(`/users/${username}`);
    }

    const createUser = ({email, username, password, name}) => {
        return api.post(`/users`,
            {
                'email': email,
                'username': username,
                'password': password,
                'name': name
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_USER
                }
            });
    }

    const deleteUser = (url) => {
        return api.delete(url);
    }

    const editUser = ({url, name}) => {
        return api.put(url,
            {
                'name': name
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_USER
                }
            });
    }

    const changePassword = ({url, currentPassword, newPassword}) => {
        console.log(currentPassword + '.' + newPassword);
        return api.put(url,
            {
                'currentPassword': currentPassword,
                'newPassword': newPassword
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_USER_PASSWORD
                }
            });
    }

    const createPasswordResetToken = (email) => {
        return api.post(`/users/password-token`, {'email': email});
    }

    const resetPassword = ({token, newPassword}) => {
        return api.put(`/users/password-token/${token}`,
            {
                'newPassword': newPassword
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_USER_PASSWORD
                }
            });
    }

    const sendVerificationToken = (email) => {
        return api.post(`/users/verification-token`,
            {
                'email': email
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_USER
                }
            });
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
        getUserByUsername,
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