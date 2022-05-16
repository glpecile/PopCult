import api from './api'
import {VndType} from "../enums/VndType";

const reportApi = (() => {

    const getListReports = ({page, pageSize}) => {
        return api.get(`/lists-reports`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getListReport = (id) => {
        return api.get(`/lists-reports/${id}`);
    }

    const approveListReport = (url) => {
        return api.put(url);
    }

    const deleteListReport = (url) => {
        return api.delete(url);
    }

    const getListCommentReports = ({page, pageSize}) => {
        return api.get(`/lists-comments-reports`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getListCommentReport = (id) => {
        return api.get(`/lists-comments-reports/${id}`);
    }

    const approveListCommentReport = (url) => {
        return api.put(url);
    }

    const deleteListCommentReport = (url) => {
        return api.delete(url);
    }

    const getMediaCommentReports = ({page, pageSize}) => {
        return api.get(`/media-comments-reports`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const getMediaCommentReport = (id) => {
        return api.get(`/media-comment-reports/${id}`);
    }

    const approveMediaCommentReport = (url) => {
        return api.put(url);
    }

    const deleteMediaCommentReport = (url) => {
        return api.delete(url);
    }

    const getReportsFromList = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const createListReport = ({url, data}) => {
        return api.post(url,
            {
                'report': data
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS_REPORTS
                }
            });
    }

    const getReportsFromListComment = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const createListCommentReport = ({url, data}) => {
        return api.post(url,
            {
                'report': data
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_LISTS_COMMENTS_REPORTS
                }
            });
    }

    const getReportsFromMediaComment = ({url, page, pageSize}) => {
        return api.get(url,
            {
                params: {
                    'page': page,
                    'page-size': pageSize
                }
            });
    }

    const createMediaCommentReport = ({url, data}) => {
        return api.post(url,
            {
                'report': data
            },
            {
                headers: {
                    'Content-Type': VndType.APPLICATION_MEDIA_COMMENTS_REPORTS
                }
            });
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

export default reportApi;