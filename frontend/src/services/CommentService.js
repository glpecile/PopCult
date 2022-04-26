import commentApi from '../api/CommentApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const commentService = (() => {

    const getMediaComments = async ({url, page, pageSize}) => {
        const res = await commentApi.getMediaComments({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }


    //TODO define commentDto
    const createMediaComment = async (url) => {
        await commentApi.createMediaComment(url);
    }

    const getListComments = async ({url, page, pageSize}) => {
        const res = await commentApi.getListComments({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }


    //TODO define commentDto
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