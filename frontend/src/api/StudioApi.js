import api from './api'

const studioApi = (() => {

    const getMediaStudios = (url) => {
        return api.get(url);
    }

    const getStudios = ({page, pageSize}) => {
        return api.get(`/studios`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getStudio = (id) => {
        return api.get(`/studios/${id}`);
    }

    return {
        getMediaStudios,
        getStudios,
        getStudio
    }
})();

export default studioApi;