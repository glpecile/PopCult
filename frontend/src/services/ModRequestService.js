import modRequestApi from '../api/ModRequestApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const modRequestService = (() => {

    const getModRequests = async ({page, pageSize}) => {
        const res = await modRequestApi.getModRequests({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
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