import api from './api'

const listApi = (() => {

    //Retrieve the lists that contain media id
    const getMediaLists = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getLists = ({page, pageSize, query}) => {
        return api.get(`/lists`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(query && {'query': query})
                }
            });
    }

    //TODO define listCreateDto
    const createList = ({name, description, visible, collaborative}) => {
        return api.post(`/lists`,
            {
                'name': name,
                'description': description,
                'visible': visible,
                'collaborative': collaborative
            });
    }

    const getList = (url) => {
     return api.get(url);
    }

    const getListById = (id) => {
        return api.get(`/lists/${id}`);
    }

    //TODO define listEditDto
    const editList = ({url}) => {
        return api.put(url);
    }

    const deleteList = (url) => {
        return api.delete(url);
    }

    const getMediaInList = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const isMediaInList = (url) => {
        return api.get(url);
    }

    const addMediaToList = (url, data) => {
        return api.patch(url, {
            "media": data
        });
    }

    const removeMediaFromList = (url) => {
        return api.delete(url);
    }

    const getListForks = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const forkList = (url) => {
        return api.post(url);
    }

    return {
        getMediaLists,
        getLists,
        createList,
        getList,
        getListById,
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