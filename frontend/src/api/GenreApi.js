import api from './api'

const genreApi = (() => {

    const getMediaGenres = (url) => {
        return api.get(url);
    }

    const getGenres = () => {
        return api.get(`/genres`);
    }

    const getGenre = (id) => {
        return api.get(`/genres/${id}`);
    }

    return {
        getMediaGenres,
        getGenres,
        getGenre
    }
})();

export default genreApi;