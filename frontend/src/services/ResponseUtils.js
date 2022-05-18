import {parseLinkHeader} from '@web3-storage/parse-link-header'

export const parsePaginatedResponse = (response) => {
    const links = parseLinkHeader(response.headers.link);
    const data = response.data;
    return {links, data};
}