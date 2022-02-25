import api from './api'

const userApi = (() => {

    const login = ({username, password}) => {
        return api.post('/authenticate', {username: username, password: password});
    }

    const getUser = (username) => {
        return api.get(`/users/${username}`);
    }

    const uploadUserImage = ({username, formData}) => {
        return api.put(`/users/${username}/image`, formData);
    }

    const editUser = ({username, name}) => {
        return api.put(`users/${username}`, {name: name});
    }

    const deleteUser = (username) => {
        return api.delete(`/users/${username}`);
    }

    const changePassword = ({username, currentPassword, newPassword}) => {
        console.log(currentPassword + '.' + newPassword);
        return api.put(`/users/${username}/password`, {currentPassword, newPassword});
    }
    return {
        login,
        getUser,
        editUser,
        deleteUser,
        uploadUserImage,
        changePassword
    };

})();

export default userApi;