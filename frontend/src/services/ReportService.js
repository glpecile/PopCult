import reportApi from '../api/ReportApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'

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

    const approveListReport = async (url) => {
        await reportApi.approveListReport(url);
    }

    const deleteListReport = async (url) => {
        await reportApi.deleteListReport(url);
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

    const approveListCommentReport = async (url) => {
        await reportApi.approveListCommentReport(url);
    }

    const deleteListCommentReport = async (url) => {
        await reportApi.deleteListCommentReport(url);
    }

    const getMediaCommentReports = async ({page, pageSize}) => {
        const res = await reportApi.getMediaCommentReports({page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getMediaCommentReport = async (id) => {
        const res = await reportApi.getMediaCommentReport(id);
        return res.data;
    }

    const approveMediaCommentReport = async (url) => {
        await reportApi.approveMediaCommentReport(url);
    }

    const deleteMediaCommentReport = async (url) => {
        await reportApi.deleteMediaCommentReport(url);
    }

    const getReportsFromList = async ({url, page, pageSize}) => {
        const res = await reportApi.getReportsFromList({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const createListReport = async ({url, data}) => {
        return await reportApi.createListReport({url, data});
    }

    const getReportsFromListComment = async ({url, page, pageSize}) => {
        const res = await reportApi.getReportsFromListComment({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const createListCommentReport = async ({url, data}) => {
        return await reportApi.createListCommentReport({url, data});
    }

    const getReportsFromMediaComment = async ({url, page, pageSize}) => {
        const res = await reportApi.getReportsFromMediaComment({url, page, pageSize});
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const createMediaCommentReport = async ({url, data}) => {
        return await reportApi.createMediaCommentReport({url, data});
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