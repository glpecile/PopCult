import api from './api'

const mediaApi = (() => {

    //TODO define sortType enum
    const getMediaList = ({page, pageSize, mediaType, genres, sortType, decades, query}) => {
        return api.get(`/media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(mediaType && {'type': mediaType}),
                    ...(genres && {'genres': genres}),
                    ...(sortType && {'sort-type': sortType}),
                    ...(decades && {'decades': decades}),
                    ...(query && {'query': query})
                }
            });
    }

    const getMedia = (id) => {
        return api.get(`/media/${id}`);
    }


    return {
        getMediaList,
        getMedia
    }
})();

export default mediaApi;