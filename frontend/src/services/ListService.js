import listApi from '../api/ListApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const listService = (() => {

    /**
     * @param url: user.listsUrl ||
     *             user.publicListsUrl ||
     *             user.editableListsUrl ||
     *             media.listsContainUrl || ...
     */
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

    const createList = async ({name, description, isPublic, isCollaborative}) => {
        const response = await listApi.createList({
            name: name,
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

    const editList = async ({url, title, description, isPublic, isCollaborative}) => {
        await listApi.editList({
            url,
            name: title,
            description: description,
            visible: isPublic,
            collaborative: isCollaborative
        });
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
        forkList
    }
})();

export default listService;