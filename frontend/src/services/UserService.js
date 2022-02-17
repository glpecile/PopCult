import userApi from "../api/UserApi"

const UserService = (function () {

    async function getUser(username) {
        const res = await userApi.getUser(username).catch(
            e => {
                console.log(e)
                //    aca va el manejo de errores
            }
        );
        return res.data;
    }

    async function login({username, password}) {
        const res = await userApi.login({username, password});
        return res.headers.authorization.split(' ')[1];
    }

    return {
        getUser,
        login
    };

})();

export default UserService;