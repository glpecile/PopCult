import userApi from "../api/UserApi"

const UserService = (function () {

    const getUser = async (username) => {
        const res = await userApi.getUser(username);
        return res.data;
    }

    // const editUser = async (username) => {
    //
    // }

    const login = async ({username, password}) => {
        const res = await userApi.login({username, password});
        return res.headers.authorization.split(' ')[1];
    }

    return {
        getUser,
        login
    };

})();

export default UserService;