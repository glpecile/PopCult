import api from './api'

const staffApi = (() => {

    const getMediaStaff = ({url, staffType}) => {
        return api.get(url,
            {
                params: {
                    ...(staffType && {'type': staffType})
                }
            });
    }

    const getStaffMembers = ({page, pageSize, staffType}) => {
        return api.get(`/staff`,
            {
                params: {
                    'page': page,
                    'page-size': pageSize,
                    ...(staffType && {'type': staffType})
                }
            });
    }

    const getStaffMember = (id) => {
        return api.get(`/staff/${id}`);
    }

    return {
        getMediaStaff,
        getStaffMembers,
        getStaffMember
    }
})();

export default staffApi;