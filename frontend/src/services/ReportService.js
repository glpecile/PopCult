import reportApi from '../api/ReportApi'
import parse from 'parse-link-header'

const reportService = (() => {

    const getListReports = async ({page, pageSize}) => {
        const res = await reportApi.getListReports({page, pageSize});
        const links = parse(res.headers.link);
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
        const links = parse(res.headers.link);
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
        const links = parse(res.headers.link);
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
        deleteMediaCommentReport
    }
})();

export default reportService;