import userApi from "../api/UserApi"

const UserService = (function () {

    async function getUser(username) {
        const res = await userApi.getUser(username).catch(
            e => {
                console.log(e.response.data)
                //    aca va el manejo de errores
            }
        );
        return res.data;
    }

    async function login({username, password}) {
        const res = await userApi.login({username, password}).catch(e => {
            console.log(e)
            //    aca va el manejo de errores
        });
        if (!res) return null;
        return res.headers.authorization.split(' ')[1];
    }

    return {
        getUser,
        login
    };

})();

export default UserService;