import {useEffect, useState} from "react";
import React from "react";
import GenreService from "../services/GenreService";
import useErrorStatus from "../hooks/useErrorStatus";

const GenresContext = React.createContext({});

export const GenresContextProvider = (props) => {
    const [genres, setGenres] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function fillGenres() {
            try {
                const data = await GenreService.getGenres();
                setGenres(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        fillGenres();
    }, [setErrorStatusCode]);

    return <GenresContext.Provider
        value={{genres: genres}}>{props.children}</GenresContext.Provider>
}

export default GenresContext;