import collabRequestApi from '../api/CollabRequestApi'

const collabRequestService = (() => {

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
        getCollaborationRequest,
        acceptCollaborationRequest,
        deleteCollaborationRequest
    }
})();

export default collabRequestService;
