import api from './api'

const notificationApi = (() => {

    const getNotification = (id) => {
        return api.get(`/notifications/${id}`);
    }

    const setNotificationAsOpened = (id) => {
        return api.put(`/notifications/${id}`);
    }

    const deleteNotification = (id) => {
        return api.delete(`/notifications/${id}`);
    }

    return {
        getNotification,
        setNotificationAsOpened,
        deleteNotification
    }
})();

export default notificationApi;