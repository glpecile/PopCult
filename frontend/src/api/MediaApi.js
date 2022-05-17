import api from './api'

const mediaApi = (() => {

    const getMediaList = ({page, pageSize, mediaType, genres, sortType, decades, query, notInList}) => {
        return api.get(`/media`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(mediaType && {'type': mediaType}),
                    ...(genres && {'genres': genres}),
                    ...(sortType && {'sort-type': sortType}),
                    ...(decades && {'decade': decades}),
                    ...(query && {'query': query}),
                    ...(notInList && {'not-in-list': notInList})
                }
            });
    }

    const getMedia = (id) => {
        return api.get(`/media/${id}`);
    }

    const getMediaByUrl = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
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

    const getMediaFromStaff = ({url, page, pageSize, staffRole}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(staffRole && {'role': staffRole})
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
        getMediaByUrl,
        getGenreMedia,
        getMediaFromStaff: getMediaFromStaff,
        getStudioMedia
    }
})();

export default mediaApi;