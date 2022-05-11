import genreApi from '../api/GenreApi';

const genreService = (() => {

    const getMediaGenres = async (url) => {
        const res = await genreApi.getMediaGenres(url);
        return res.data;
    }

    const getGenres = async () => {
        const res = await genreApi.getGenres();
        return res.data;
    }

    const getGenre = async (id) => {
        const res = await genreApi.getGenre(id);
        return res.data;
    }

    return {
        getMediaGenres,
        getGenres,
        getGenre
    }
})();

export default genreService;