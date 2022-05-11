import api from './api'
import {VndType} from '../enums/VndType';

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
    const createMediaComment = ({url, data}) => {
        return api.post(url,
            {
                'body': data
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_MEDIA_COMMENTS
                }
            });
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
    const createListComment = ({url, data}) => {
        return api.post(url,
            {
                'body': data
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS_COMMENTS
                }
            });
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
