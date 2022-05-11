import api from './api'

const notificationApi = (() => {

    const getUserNotifications = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getNotification = (id) => {
        return api.get(`/notifications/${id}`);
    }

    const setNotificationAsOpened = (url) => {
        return api.put(url);
    }

    const deleteNotification = (url) => {
        return api.delete(url);
    }

    return {
        getUserNotifications,
        getNotification,
        setNotificationAsOpened,
        deleteNotification
    }
})();

export default notificationApi;