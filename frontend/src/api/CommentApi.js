import api from './api'

const commentApi = (() => {

    const getMediaComments = ({id, page, pageSize}) => {
        return api.get(`/media/${id}/comments`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO define commentDto
    const createMediaComment = (id) => {
        return api.post(`/media/${id}/comments`);
    }

    const getListComments = ({id, page, pageSize}) => {
        return api.get(`/lists/${id}/comments`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO define commentDto
    const createListComment = (id) => {
        return api.post(`/lists/${id}/comments`);
    }

    const getMediaComment = (id) => {
        return api.get(`/media-comments/${id}`);
    }

    const deleteMediaComment = (id) => {
        return api.delete(`/media-comments/${id}`);
    }

    const getListComment = (id) => {
        return api.get(`/lists-comments/${id}`);
    }

    const deleteListComment = (id) => {
        return api.delete(`/lists-comments/${id}`);
    }

    return {
        getMediaComments,
        createMediaComment,
        getListComments,
        createListComment,
        getMediaComment,
        deleteMediaComment,
        getListComment,
        deleteListComment
    }
})();

export default commentApi;
