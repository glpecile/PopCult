import api from './api'

const collaborativeApi = (() => {

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

    const getListCollaborators = ({id, page, pageSize}) => {
        return api.get(`/lists/${id}/collaborators`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isListCollaborator = ({listId, username}) => {
        return api.get(`/lists/${listId}/collaborators/${username}`);
    }

    const addListCollaborator = ({listId, username}) => {
        return api.put(`/lists/${listId}/collaborators/${username}`);
    }

    const deleteListCollaborator = ({listId, username}) => {
        return api.delete(`/lists/${listId}/collaborators/${username}`);
    }

    const getListCollaborationRequests = ({id, page, pageSize}) => {
        return api.get(`/lists/${id}/requests`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO Define dto
    const createListCollaborationRequest = ({id}) => {
        return api.post(`/lists/${id}/requests`);
    }

    return {
        getUserCollaborationRequests,
        getCollaborationRequest,
        acceptCollaborationRequest,
        deleteCollaborationRequest,
        getListCollaborators,
        isListCollaborator,
        addListCollaborator,
        deleteListCollaborator,
        getListCollaborationRequests,
        createListCollaborationRequest
    }
})();

export default collaborativeApi;