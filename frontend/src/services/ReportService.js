import reportApi from '../api/ReportApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import api from "../api/api";

const reportService = (() => {

    const getListReports = async ({page, pageSize}) => {
        const res = await reportApi.getListReports({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getListReport = async (id) => {
        const res = await reportApi.getListReport(id);
        return res.data;
    }

    const approveListReport = async (id) => {
        await reportApi.approveListReport(id);
    }

    const deleteListReport = async (id) => {
        await reportApi.deleteListReport(id);
    }

    const getListCommentReports = async ({page, pageSize}) => {
        const res = await reportApi.getListCommentReports({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getListCommentReport = async (id) => {
        const res = await reportApi.getListCommentReport(id);
        return res.data;
    }

    const approveListCommentReport = async (id) => {
        await reportApi.approveListCommentReport(id);
    }

    const deleteListCommentReport = async (id) => {
        await reportApi.deleteListCommentReport(id);
    }

    const getMediaCommentReports = async ({page, pageSize}) => {
        const res = await reportApi.getMediaCommentReports({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getMediaCommentReport = async (id) => {
        const res = await reportApi.getMediaCommentReport(id);
    }

    const approveMediaCommentReport = async (id) => {
        await reportApi.approveMediaCommentReport(id);
    }

    const deleteMediaCommentReport = async (id) => {
        await reportApi.deleteMediaCommentReport(id);
    }

    const getReportsFromList = async ({id, page, pageSize}) => {
        const res = await reportApi.getReportsFromList({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    //TODO define dto
    const createListReport = async (id) => {

    }

    const getReportsFromListComment = async ({id, page, pageSize}) => {
        const res = await reportApi.getReportsFromListComment({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    //TODO define dto
    const createListCommentReport = async (id) => {

    }

    const getReportsFromMediaComment = async ({id, page, pageSize}) => {
        const res = await reportApi.getReportsFromMediaComment({id, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    //TODO define dto
    const createMediaCommentReport = async (id) => {

    }

    return {
        getListReports,
        getListReport,
        approveListReport,
        deleteListReport,
        getListCommentReports,
        getListCommentReport,
        approveListCommentReport,
        deleteListCommentReport,
        getMediaCommentReports,
        getMediaCommentReport,
        approveMediaCommentReport,
        deleteMediaCommentReport,
        getReportsFromList,
        createListReport,
        getReportsFromListComment,
        createListCommentReport,
        getReportsFromMediaComment,
        createMediaCommentReport
    }
})();

export default reportService;