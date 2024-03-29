import collaborativeApi from '../api/CollaborativeApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {parsePaginatedResponse} from "./ResponseUtils";

const collaborativeService = (() => {

    const getUserCollaborationRequests = async ({url, page, pageSize}) => {
        const res = await collaborativeApi.getUserCollaborationRequests({url, page, pageSize})
        return parsePaginatedResponse(res);
    }

    const getCollaborationRequest = async (id) => {
        const res = await collaborativeApi.getCollaborationRequest(id);
        return res.data;
    }

    const acceptCollaborationRequest = async (url) => {
        await collaborativeApi.acceptCollaborationRequest(url);
    }

    const deleteCollaborationRequest = async (url) => {
        await collaborativeApi.deleteCollaborationRequest(url);
    }

    const getListCollaborators = async ({url, page, pageSize}) => {
        const res = await collaborativeApi.getListCollaborators({url, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isListCollaborator = async ({id, username}) => {
        const res = await collaborativeApi.isListCollaborator({id, username});
        return res.data;
    }

    const manageListCollaborators = async ({url, add, remove}) => {
        await collaborativeApi.manageListCollaborators({url, add, remove});
    }

    const addListCollaborator = async (url) => {
        await collaborativeApi.addListCollaborator(url);
    }

    const deleteListCollaborator = async (url) => {
        await collaborativeApi.deleteListCollaborator(url);
    }

    const getListCollaborationRequests = async ({url, page, pageSize}) => {
        const res = await collaborativeApi.getListCollaborationRequests({url, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const createListCollaborationRequest = async (url) => {
        await collaborativeApi.createListCollaborationRequest(url);
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

export default collaborativeService;
