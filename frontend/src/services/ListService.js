import listApi from '../api/ListApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const listService = (() => {

    //TODO
    const getMediaLists = async ({url, page, pageSize}) => {
        const res = await listApi.getMediaLists({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getLists = async ({page, pageSize, query, genres, sortType, decades}) => {
        const res = await listApi.getLists({page, pageSize, query, genres, sortType, decades});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    //TODO define listCreateDto
    const createList = async (title, description, isPublic, isCollaborative) => {
        const response = await listApi.createList({
            name: title,
            description: description,
            visible: isPublic,
            collaborative: isCollaborative
        });
        return response.headers.location;
    }

    const getList = async (url) => {
        const res = await listApi.getList(url);
        return res.data;
    }

    const getListById = async (id) => {
        const res = await listApi.getListById(id);
        return res.data;
    }

    //TODO define listEditDto
    const editList = async () => {

    }

    const deleteList = async (url) => {
        await listApi.deleteList(url);
    }

    const getMediaInList = async ({url, page, pageSize}) => {
        const res = await listApi.getMediaInList({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isMediaInList = async (url) => {
        const res = await listApi.isMediaInList(url);
        return res.data;
    }

    const manageMediaInList = async ({url, add, remove}) => {
        await listApi.manageMediaInList({url, add, remove})
    }

    const addMediaToList = async (url) => {
        await listApi.addMediaToList(url);
    }

    const removeMediaFromList = async (url) => {
        await listApi.removeMediaFromList(url);
    }

    const getListForks = async ({url, page, pageSize}) => {
        const res = await listApi.getListForks({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const forkList = async (url) => {
        const response = await listApi.forkList(url);
        return response.headers.location;

    }

    const getUserEditableListsByUsername = async ({username, page, pageSize}) => {
        const res = await listApi.getUserEditableListsByUsername({username, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
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

export default listService;