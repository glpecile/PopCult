import staffApi from '../api/StaffApi'
import {StaffRole} from '../enums/StaffRole'
import {parsePaginatedResponse} from "./ResponseUtils";

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
        return parsePaginatedResponse(res);
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
