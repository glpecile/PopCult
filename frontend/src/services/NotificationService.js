import notificationApi from '../api/NotificationApi'

const notificationService = (() => {

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
        getNotification,
        setNotificationAsOpened,
        deleteNotification
    }
})();

export default notificationService;

