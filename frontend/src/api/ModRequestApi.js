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

    const promoteToMod = (url) => {
        return api.put(url);
    }

    const rejectModRequest = (url) => {
        return api.delete(url);
    }

    return {
        getModRequests,
        getModRequest,
        promoteToMod,
        rejectModRequest
    }
})();

export default modRequestApi;