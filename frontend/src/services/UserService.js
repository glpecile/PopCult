import userApi from "../api/UserApi"
import axios from "axios";

const UserService = (function () {

    const getUser = async (username) => {
        const res = await userApi.getUser(username).catch(
            e => {
                return axios.isCancel(e) ? e : e.response;
                //    aca va el manejo de errores
            }
        );
        return res.data;
    }

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