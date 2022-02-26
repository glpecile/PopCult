import collabRequestApi from '../api/CollabRequestApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const collabRequestService = (() => {

    const getUserCollaborationRequests = async ({username, page, pageSize}) => {
        const res = await collabRequestApi.getUserCollaborationRequests({username, page, pageSize})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getCollaborationRequest = async (id) => {
        const res = await collabRequestApi.getCollaborationRequest(id);
        return res.data;
    }

    const acceptCollaborationRequest = async (id) => {
        await collabRequestApi.acceptCollaborationRequest(id);
    }

    const deleteCollaborationRequest = async (id) => {
        await collabRequestApi.deleteCollaborationRequest(id);
    }

    return {
        getUserCollaborationRequests,
        getCollaborationRequest,
        acceptCollaborationRequest,
        deleteCollaborationRequest
    }
})();

export default collabRequestService;
