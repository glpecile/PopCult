import notificationApi from '../api/NotificationApi'
import {parsePaginatedResponse} from "./ResponseUtils";

const notificationService = (() => {

    const getUserNotifications = async ({url, page, pageSize}) => {
        const res = await notificationApi.getUserNotifications({url, page, pageSize})
        return parsePaginatedResponse(res);
    }

    const getNotification = async (id) => {
        const res = await notificationApi.getNotification(id);
        return res.data;
    }

    const setNotificationAsOpened = async (url) => {
        await notificationApi.setNotificationAsOpened(url);
    }

    const deleteNotification = async (url) => {
        await notificationApi.deleteNotification(url);
    }

    return {
        getUserNotifications,
        getNotification,
        setNotificationAsOpened,
        deleteNotification
    }
})();

export default notificationService;

