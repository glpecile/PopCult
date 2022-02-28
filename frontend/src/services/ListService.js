import listApi from '../api/ListApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const listService = (() => {

    //TODO
    const getMediaLists = async (id) => {

    }

    const getLists = async ({page, pageSize}) => {
        const res = await listApi.getLists({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    //TODO define listCreateDto
    const createList = async () => {

    }

    const getList = async (id) => {
        const res = await listApi.getList(id);
        return res.data;
    }

    //TODO define listEditDto
    const editList = async () => {

    }

    const deleteList = async (id) => {
        await listApi.deleteList(id);
    }

    const getMediaInList = async ({id, page, pageSize}) => {
        const res = await listApi.getMediaInList({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isMediaInList = async ({listId, mediaId}) => {
        const res = await listApi.isMediaInList({listId, mediaId});
        return res.data;
    }

    const addMediaToList = async ({listId, mediaId}) => {
        await listApi.addMediaToList({listId, mediaId});
    }

    const removeMediaFromList = async ({listId, mediaId}) => {
        await listApi.removeMediaFromList({listId, mediaId});
    }

    const getListForks = async ({id, page, pageSize}) => {
        const res = await listApi.getListForks({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const forkList = async (id) => {
        await listApi.forkList(id);
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

export default listService;