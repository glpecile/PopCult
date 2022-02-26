import api from './api'

const collabRequestApi = (() => {

    const getUserCollaborationRequests = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/collab-requests`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getCollaborationRequest = (id) => {
        return api.get(`/collab-requests/${id}`);
    }

    const acceptCollaborationRequest = (id) => {
        return api.put(`/collab-requests/${id}`);
    }

    const deleteCollaborationRequest = (id) => {
        return api.delete(`/collab-requests/${id}`);
    }

    return {
        getUserCollaborationRequests,
        getCollaborationRequest,
        acceptCollaborationRequest,
        deleteCollaborationRequest
    }
})();

export default collabRequestApi;