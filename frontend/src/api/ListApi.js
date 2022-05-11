import api from './api'
import {VndType} from '../enums/VndType';

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

    const getLists = ({page, pageSize, query, genres, sortType, decades}) => {
        return api.get(`/lists`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(genres && {'genres': genres}),
                    ...(sortType && {'sort-type': sortType}),
                    ...(decades && {'decade': decades}),
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
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS,
                }
            });
    }

    const getList = (url) => {
        return api.get(url);
    }

    const getListById = (id) => {
        return api.get(`/lists/${id}`);
    }

    //TODO define listEditDto
    const editList = ({url, name, description, visible, collaborative}) => {
        return api.put(url,
            {
                'name': name,
                'description': description,
                'visible': visible,
                'collaborative': collaborative
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS,
                }
            });
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

    const manageMediaInList = ({url, add, remove}) => {
        return api.patch(url, {
                'add': add,
                'remove': remove
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS_PATCH_MEDIA
                }
            });
    }

    const addMediaToList = (url, data) => {
        return api.patch(url, {
                'media': data
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS_PATCH_MEDIA
                }
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

    const getUserEditableListsByUsername = ({username, page, pageSize}) => {
        return api.get(`/users/${username}/editable-lists`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
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
        manageMediaInList,
        addMediaToList,
        removeMediaFromList,
        getListForks,
        forkList,
        getUserEditableListsByUsername
    }
})();

export default listApi;