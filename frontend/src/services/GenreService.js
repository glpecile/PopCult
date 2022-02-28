import genreApi from '../api/GenreApi';
import {parseLinkHeader} from '@web3-storage/parse-link-header'

const genreService = (() => {

    const getGenres = async () => {
        const res = await genreApi.getGenres();
        return res.data;
    }

    const getGenre = async (id) => {
        const res = await genreApi.getGenre(id);
        return res.data;
    }

    return {
        getGenres,
        getGenre
    }
})();

export default genreService;