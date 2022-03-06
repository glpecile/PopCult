import userApi from '../api/UserApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {UserRole} from '../enums/UserRole'

const UserService = (function () {

    const login = async ({username, password}) => {
        const res = await userApi.login({username, password});
        return res.headers.authorization.split(' ')[1];
    }

    const getUsers = async ({page, pageSize, userRole, banned}) => {
        const res = await userApi.getUsers({page, pageSize, userRole, banned});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data}
    }

    const getModerators = async ({page, pageSize}) => {
        return await getUsers({page, pageSize, userRole: UserRole.MOD});
    }

    const getBannedUsers = async ({page, pageSize}) => {
        return await getUsers({page, pageSize, userRole: null, banned: true});
    }

    const getUser = async (username) => {
        const res = await userApi.getUser(username);
        return res.data;
    }

    const createUser = async ({email, username, password, name}) => {
        await userApi.createUser({email, username, password, name});
        //el response retorna el header location con la url del usuario nuevo. Se puede llegar a usar
    }

    const deleteUser = async (url) => {
        await userApi.deleteUser(url);
    }

    const editUser = async ({url, name}) => {
        await userApi.editUser({url, name});
    }

    const changeUserPassword = async ({url, currentPassword, newPassword}) => {
        await userApi.changePassword({url, currentPassword, newPassword})
    }

    const createPasswordResetToken = async (email) => {
        await userApi.createPasswordResetToken(email);
        //Retorna tanto location del reset-password como una entidad con el token
    }

    const resetPassword = async ({token, newPassword}) => {
        await userApi.resetPassword({token, newPassword});
    }

    const sendVerificationToken = async (email) => {
        await userApi.sendVerificationToken(email);
    }

    const verifyUser = async ({token}) => {
        const res = await userApi.verifyUser(token);
        return res.headers.authorization.split(' ')[1];

    }

    const uploadUserImage = async ({url, formData}) => {
        await userApi.uploadUserImage({url: url, formData: formData});
    }

    const deleteProfileImage = async (url) => {
        await userApi.deleteProfileImage(url);
    }

    const createModRequest = async (username) => {
        await userApi.createModRequest(username);
    }

    const removeMod = async (url) => {
        await userApi.removeMod(url);
    }

    const banUser = async (username) => {
        await userApi.banUser(username);
    }

    const unbanUser = async (url) => {
        await userApi.unbanUser(url);
    }

    return {
        login,
        getUsers,
        getModerators,
        getBannedUsers,
        getUser,
        createUser,
        deleteUser,
        editUser,
        changeUserPassword,
        createPasswordResetToken,
        resetPassword,
        sendVerificationToken,
        verifyUser,
        uploadUserImage,
        deleteProfileImage,
        createModRequest,
        removeMod,
        banUser,
        unbanUser,
    };
})();

export default UserService;