import api from './api'

const userApi = (function () {
    const login = ({email, password}) => {
        return api.post('/authenticate', {email: email, password: password});
    }

    const getUser = (username) => {
        return api.get(`/users/${username}`);
    }
    return {login, getUser};
})();

export default userApi;