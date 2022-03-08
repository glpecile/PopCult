import studioApi from '../api/StudioApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const studioService = (() => {

    const getMediaStudios = async (url) => {
        const res = await studioApi.getMediaStudios(url);
        return res.data;
    }

    const getStudios = async ({page, pageSize}) => {
        const res = await studioApi.getStudios({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getStudio = async (id) => {
        const res = await studioApi.getStudio(id);
        return res.data;
    }

    return {
        getMediaStudios,
        getStudios,
        getStudio
    }
})();

export default studioService;