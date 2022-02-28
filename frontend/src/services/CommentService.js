import commentApi from '../api/CommentApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const commentService = (() => {

    const getMediaComments = async ({id, page, pageSize}) => {
        const res = await commentApi.getMediaComments({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }


    //TODO define commentDto
    const createMediaComment = async (id) => {
        await commentApi.createMediaComment(id);
    }

    const getListComments = async ({id, page, pageSize}) => {
        const res = await commentApi.getListComments({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }


    //TODO define commentDto
    const createListComment = async (id) => {
        await commentApi.createListComment(id);
    }

    const getMediaComment = async (id) => {
        const res = await commentApi.getMediaComment(id);
        return res.data;
    }

    const deleteMediaComment = async (id) => {
        await commentApi.deleteMediaComment(id);
    }

    const getListComment = async (id) => {
        const res = await commentApi.getListComment(id);
        return res.data;
    }

    const deleteListComment = async (id) => {
        await commentApi.deleteListComment(id);
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