import staffApi from '../api/StaffApi'
import {parseLinkHeader} from '@web3-storage/parse-link-header'
import {StaffRole} from '../enums/StaffRole'

const staffService = (() => {

    const getMediaStaff = async ({url, staffType}) => {
        const res = await staffApi.getMediaStaff({url, staffType});
        return res.data
    }

    const getMediaDirectors = async (url) => {
        return await getMediaStaff({url, staffType: StaffRole.DIRECTOR})
    }

    const getMediaCrew = async (url) => {
        return await getMediaStaff({url, staffType: StaffRole.ACTOR})
    }

    const getStaffMembers = async ({page, pageSize, staffType}) => {
        const res = await staffApi.getStaffMembers({page, pageSize, staffType})
        const links = parseLinkHeader(res.headers.link);
        const data = res.data;
        return {links, data};
    }

    const getStaffMember = async (id) => {
        const res = await staffApi.getStaffMember(id)
        return res.data;
    }

    return {
        getMediaStaff,
        getMediaDirectors,
        getMediaCrew,
        getStaffMembers,
        getStaffMember
    }
})();

export default staffService;
