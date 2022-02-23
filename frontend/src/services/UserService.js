import userApi from "../api/UserApi"

const UserService = (function () {

    const getUser = async (username) => {
        const res = await userApi.getUser(username);
        return res.data;
    }

    const editUser = async ({username, name}) => {
        await userApi.editUser({username, name});
    }

    const deleteUser = async () => {

    }

    const login = async ({username, password}) => {
        const res = await userApi.login({username, password});
        return res.headers.authorization.split(' ')[1];
    }

    const uploadUserImage = async ({username, formData}) =>{
        await userApi.uploadUserImage({username: username, formData: formData});
    }

    return {
        getUser,
        editUser,
        deleteUser,
        login,
        uploadUserImage,
    };

})();

export default UserService;