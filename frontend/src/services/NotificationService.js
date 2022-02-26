import notificationApi from '../api/NotificationApi'
import {parseLinkHeader} from "@web3-storage/parse-link-header";

const notificationService = (() => {

    const getUserNotifications = async ({username, page, pageSize}) => {
        const res = await notificationApi.getUserNotifications({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getNotification = async (id) => {
        const res = await notificationApi.getNotification(id);
        return res.data;
    }

    const setNotificationAsOpened = async (id) => {
        await notificationApi.setNotificationAsOpened(id);
    }

    const deleteNotification = async (id) => {
        await notificationApi.deleteNotification(id);
    }

    return {
        getUserNotifications,
        getNotification,
        setNotificationAsOpened,
        deleteNotification
    }
})();

export default notificationService;

