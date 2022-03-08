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

    const getGenreMedia = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getStaffMedia = ({url, page, pageSize, staffRole}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(staffRole && {'type': staffRole})
                }
            });
    }

    const getStudioMedia = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    return {
        getMediaList,
        getMedia,
        getGenreMedia,
        getStaffMedia,
        getStudioMedia
    }
})();

export default mediaApi;