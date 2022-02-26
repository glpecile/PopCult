import api from './api'

const notificationApi = (() => {

    const getUserNotifications = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/notifications`,
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

    const setNotificationAsOpened = (id) => {
        return api.put(`/notifications/${id}`);
    }

    const deleteNotification = (id) => {
        return api.delete(`/notifications/${id}`);
    }

    return {
        getUserNotifications,
        getNotification,
        setNotificationAsOpened,
        deleteNotification
    }
})();

export default notificationApi;