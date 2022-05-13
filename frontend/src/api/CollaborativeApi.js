import api from './api'
import {VndType} from '../enums/VndType';

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

    const isListCollaborator = ({id, username}) => {
        return api.get(`/lists/${id}/collaborators/${username}`);
    }

    const manageListCollaborators = ({url, add, remove}) => {
        return api.patch(url, {
                'add': add,
                'remove': remove
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS_PATCH_COLLABORATORS
                }
            });
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

    const createListCollaborationRequest = (url) => {
        return api.post(url);
    }

    return {
        getUserCollaborationRequests,
        getCollaborationRequest,
        acceptCollaborationRequest,
        deleteCollaborationRequest,
        getListCollaborators,
        isListCollaborator,
        manageListCollaborators,
        addListCollaborator,
        deleteListCollaborator,
        getListCollaborationRequests,
        createListCollaborationRequest
    }
})();

export default collaborativeApi;