import api from './api'

const collabRequestApi = (() => {

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
        getCollaborationRequest,
        acceptCollaborationRequest,
        deleteCollaborationRequest
    }
})();

export default collabRequestApi;