import api from './api'

const collaborativeApi = (() => {

    const getUserCollaborationRequests = ({url, page, pageSize}) => {
        return api.get(url,
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

    const acceptCollaborationRequest = (url) => {
        return api.put(url);
    }

    const deleteCollaborationRequest = (url) => {
        return api.delete(url);
    }

    const getListCollaborators = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isListCollaborator = (url) => {
        return api.get(url);
    }

    const addListCollaborator = (url) => {
        return api.put(url);
    }

    const deleteListCollaborator = (url) => {
        return api.delete(url);
    }

    const getListCollaborationRequests = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO Define dto
    const createListCollaborationRequest = ({url}) => {
        return api.post(url);
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