import api from './api'

const commentApi = (() => {

    const getMediaComments = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO define commentDto
    const createMediaComment = ({url}) => {
        return api.post(url);
    }

    const getListComments = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    //TODO define commentDto
    const createListComment = ({url}) => {
        return api.post(url);
    }

    const getMediaComment = (id) => {
        return api.get(`/media-comments/${id}`);
    }

    const deleteMediaComment = (url) => {
        return api.delete(url);
    }

    const getListComment = (id) => {
        return api.get(`/lists-comments/${id}`);
    }

    const deleteListComment = (url) => {
        return api.delete(url);
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
