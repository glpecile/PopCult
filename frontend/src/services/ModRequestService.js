import modRequestApi from '../api/ModRequestApi'
import {parsePaginatedResponse} from "./ResponseUtils";

const modRequestService = (() => {

    const getModRequests = async ({page, pageSize}) => {
        const res = await modRequestApi.getModRequests({page, pageSize});
        return parsePaginatedResponse(res);
    }

    const getModRequest = async (id) => {
        const res = await modRequestApi.getModRequest(id);
        return res.data;
    }

    const promoteToMod = async (url) => {
        await modRequestApi.promoteToMod(url);
    }

    const rejectModRequest = async (url) => {
        await modRequestApi.rejectModRequest(url);
    }

    return {
        getModRequests,
        getModRequest,
        promoteToMod,
        rejectModRequest
    }
})();

export default modRequestService