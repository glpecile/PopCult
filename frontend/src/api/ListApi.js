import api from './api'

const listApi = (() => {

    //Retrieve the lists that contain media id
    // TODO define pagination
    const getMediaLists = (id) => {
        return api.get(`/media/${id}/lists`);
    }

    const getLists = ({page, pageSize}) => {
        return api.get(`/lists`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO define listCreateDto
    const createList = () => {
        return api.post(`/lists`);
    }

    const getList = (id) => {
        return api.get(`/lists/${id}`);
    }

    //TODO define listEditDto
    const editList = (id) => {
        return api.put(`/lists/${id}`);
    }

    const deleteList = (id) => {
        return api.delete(`/lists/${id}`);
    }

    const getMediaInList = ({id, page, pageSize}) => {
        return api.get(`/lists/${id}/media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isMediaInList = ({listId, mediaId}) => {
        return api.get(`/lists/${listId}/media/${mediaId}`);
    }

    const addMediaToList = ({listId, mediaId}) => {
        return api.put(`/lists/${listId}/media/${mediaId}`);
    }

    const removeMediaFromList = ({listId, mediaId}) => {
        return api.delete(`/lists/${listId}/media/${mediaId}`);
    }

    const getListForks = ({id, page, pageSize}) => {
        return api.get(`/lists/${id}/forks`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const forkList = (id) => {
        return api.post(`/lists/${id}/forks`);
    }

    return {
        getMediaLists,
        getLists,
        createList,
        getList,
        editList,
        deleteList,
        getMediaInList,
        isMediaInList,
        addMediaToList,
        removeMediaFromList,
        getListForks,
        forkList
    }
})();

export default listApi;