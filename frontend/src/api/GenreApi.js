import api from './api'

const genreApi = (() => {

    const getGenres = () => {
        return api.get(`/genres`);
    }

    const getGenre = (id) => {
        return api.get(`/genres/${id}`);
    }

    return {
        getGenres,
        getGenre
    }
})();

export default genreApi;