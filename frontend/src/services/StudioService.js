import studioApi from '../api/StudioApi'
import {parsePaginatedResponse} from "./ResponseUtils";

const studioService = (() => {

    const getMediaStudios = async (url) => {
        const res = await studioApi.getMediaStudios(url);
        return res.data;
    }

    const getStudios = async ({page, pageSize}) => {
        const res = await studioApi.getStudios({page, pageSize});
        return parsePaginatedResponse(res);
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