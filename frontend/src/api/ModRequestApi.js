import api from './api'

const modRequestApi = (() => {

    const getModRequests = ({page, pageSize}) => {
        return api.get(`/mods-requests`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getModRequest = (id) => {
        return api.get(`/mods-requests/${id}`);
    }

    const promoteToMod = (id) => {
        return api.put(`/mods-requests/${id}`);
    }

    const rejectModRequest = (id) => {
        return api.delete(`/mods-requests/${id}`);
    }

    return {
        getModRequests,
        getModRequest,
        promoteToMod,
        rejectModRequest
    }
})();

export default modRequestApi;