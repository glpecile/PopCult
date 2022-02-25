import userApi from '../api/UserApi'

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

    const uploadUserImage = async ({username, formData}) => {
        await userApi.uploadUserImage({username: username, formData: formData});
    }

    const changeUserPassword = async ({username, currentPassword, newPassword}) => {
        await userApi.changePassword({username, currentPassword, newPassword})
    }
    return {
        getUser,
        editUser,
        deleteUser,
        login,
        uploadUserImage,
        changeUserPassword
    };

})();

export default UserService;