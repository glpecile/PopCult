import commentApi from '../api/CommentApi'
import {parsePaginatedResponse} from "./ResponseUtils";

const commentService = (() => {

    const getMediaComments = async ({url, page, pageSize}) => {
        const res = await commentApi.getMediaComments({url, page, pageSize});
        return parsePaginatedResponse(res);
    }

    const createMediaComment = async ({url, data}) => {
        await commentApi.createMediaComment({url, data});
    }

    const getListComments = async ({url, page, pageSize}) => {
        const res = await commentApi.getListComments({url, page, pageSize});
        return parsePaginatedResponse(res);
    }

    const createListComment = async ({url, data}) => {
        await commentApi.createListComment({url, data});
    }

    const getMediaComment = async (id) => {
        const res = await commentApi.getMediaComment(id);
        return res.data;
    }

    const deleteMediaComment = async (url) => {
        await commentApi.deleteMediaComment(url);
    }

    const getListComment = async (id) => {
        const res = await commentApi.getListComment(id);
        return res.data;
    }

    const deleteListComment = async (url) => {
        await commentApi.deleteListComment(url);
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

export default commentService;