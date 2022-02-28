import collaborativeApi from '../api/CollaborativeApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const collaborativeService = (() => {

    const getUserCollaborationRequests = async ({username, page, pageSize}) => {
        const res = await collaborativeApi.getUserCollaborationRequests({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getCollaborationRequest = async (id) => {
        const res = await collaborativeApi.getCollaborationRequest(id);
        return res.data;
    }

    const acceptCollaborationRequest = async (id) => {
        await collaborativeApi.acceptCollaborationRequest(id);
    }

    const deleteCollaborationRequest = async (id) => {
        await collaborativeApi.deleteCollaborationRequest(id);
    }

    const getListCollaborators = async ({id, page, pageSize}) => {
        const res = await collaborativeApi.getListCollaborators({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const isListCollaborator = async ({listId, username}) => {
        const res = await collaborativeApi.isListCollaborator({listId, username});
        return res.data;
    }

    const addListCollaborator = async ({listId, username}) => {
        await collaborativeApi.addListCollaborator({listId, username});
    }

    const deleteListCollaborator = async ({listId, username}) => {
        await collaborativeApi.deleteListCollaborator({listId, username});
    }

    const getListCollaborationRequests = async ({id, page, pageSize}) => {
        const res = await collaborativeApi.getListCollaborationRequests({id, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    //TODO Define dto
    const createListCollaborationRequest = async ({id}) => {

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

export default collaborativeService;
