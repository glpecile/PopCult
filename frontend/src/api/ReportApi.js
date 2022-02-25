import api from './api'

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

    const approveListReport = (id) => {
        return api.put(`/lists-reports/${id}`);
    }

    const deleteListReport = (id) => {
        return api.delete(`/lists-reports/${id}`);
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

    const approveListCommentReport = (id) => {
        return api.put(`/lists-comments-reports/${id}`);
    }

    const deleteListCommentReport = (id) => {
        return api.delete(`/lists-comments-reports/${id}`);
    }

    const getMediaCommentReports = ({page, pageSize}) => {
        return api.get(`/media-comment-reports`,
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

    const approveMediaCommentReport = (id) => {
        return api.put(`/media-comment-reports/${id}`);
    }

    const deleteMediaCommentReport = (id) => {
        return api.delete(`/media-comment-reports/${id}`);
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

export default reportApi;