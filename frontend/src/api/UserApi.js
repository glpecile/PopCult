import api from './api'

const userApi = (() => {
    const login = ({username, password}) => {
        return api.post('/authenticate', {username: username, password: password});
    }

    const getUser = (username) => {
        return api.get(`/users/${username}`);
    }
    return {login, getUser};
})();

export default userApi;