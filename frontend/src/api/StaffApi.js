import api from './api'

const staffApi = (() => {

    //TODO add staffType enum
    const getMediaStaff = (id) => {
        return api.get(`/media/${id}/staff`);
    }

    //TODO define staffType enum
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