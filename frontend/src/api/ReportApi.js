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

    //TODO define dto
    const createListReport = ({url}) => {
        return api.post(url)
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

    //TODO define dto
    const createListCommentReport = ({url}) => {
        return api.post(url)
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

    //TODO define dto
    const createMediaCommentReport = ({url}) => {
        return api.post(url)
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