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
        await listApi.forkList(url);
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