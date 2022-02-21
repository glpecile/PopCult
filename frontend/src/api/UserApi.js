import api from './api'

const userApi = (() => {
    const login = ({username, password}) => {
        return api.post('/authenticate', {username: username, password: password});
    }

    const getUser = (username) => {
        return api.get(`/users/${username}`);
    }

    const editUser = (username, name) => {
        return api.put(`users/${username}`, {name: name});
    }

    const deleteUser = (username) => {
        return api.delete(`/users/${username}`);
    }

    return {login, getUser, editUser, deleteUser};

})();

export default userApi;