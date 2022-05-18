import {useContext, useEffect, useState} from "react";
import {useParams, useSearchParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import GenresContext from "../../../store/GenresContext";
import Loader from "../errors/Loader";
import useErrorStatus from "../../../hooks/useErrorStatus";
import MediaService from "../../../services/MediaService";
import ListService from "../../../services/ListService";
import ListsSlider from "../../../components/lists/ListsSlider";
import ResponsiveMediaGrid from "../../../components/ResponsiveMediaGrid";
import MediaCard from "../../../components/media/MediaCard";
import PaginationComponent from "../../../components/PaginationComponent";
import NoResults from "../../../components/search/NoResults";

export default function Genres() {
    const {genre: genreParam} = useParams();
    const genresFromContext = useContext(GenresContext).genres;
    const {setErrorStatusCode} = useErrorStatus();
    const [searchParams] = useSearchParams();
    const {t} = useTranslation();

    const [mediaPaginated, setMediaPaginated] = useState(undefined);
    const [listsCarrousel, setListsCarrousel] = useState(undefined);

    const [page, setPage] = useState(searchParams.get("page") || 1);
    const [pageSize] = useState(12);

    useEffect(() => {
        const fetchData = async () => {
            try {
                // fetch
                const filteredGenre = genresFromContext.filter((g) => g.genre.localeCompare(genreParam.toUpperCase()) === 0)[0];
                if (!filteredGenre)
                    setErrorStatusCode(404);
                const mediaFromGenrePromise = MediaService.getMediaByUrl({
                    url: filteredGenre.mediaUrl,
                    page: page,
                    pageSize: pageSize
                });
                const listsFromGenrePromise = ListService.getMediaLists({
                    url: filteredGenre.listsUrl,
                    page: 1,
                    pageSize: pageSize
                });
                // promise all
                const [mediaFromGenre, listsFromGenre] = await Promise.all([mediaFromGenrePromise, listsFromGenrePromise]);

                // set
                setMediaPaginated(mediaFromGenre);
                setListsCarrousel(listsFromGenre.data);
            } catch (e) {
                setErrorStatusCode(e.response.status);
            }
        }
        if (genresFromContext)
            fetchData();
    }, [genreParam, genresFromContext, page, pageSize, setErrorStatusCode])

    return (<>
        {
            (genresFromContext && mediaPaginated !== undefined && listsCarrousel !== undefined) ?
                <div className="space-y-3">
                    <h1 className="text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide">
                        {genreParam}
                    </h1>
                    <h5 className="font-bold text-2xl py-2">
                        {t('genre_lists')}
                    </h5>
                    {
                        // Lists
                        listsCarrousel ?
                            <ListsSlider lists={listsCarrousel}/> : <NoResults title={t('genre_no_lists')}/>
                    }
                    <h5 className="font-bold text-2xl py-2">
                        {t('genre_media')}
                    </h5>
                    {
                        // Media
                        (mediaPaginated && mediaPaginated.data) ?
                            <>
                                <ResponsiveMediaGrid>
                                    {
                                        mediaPaginated.data.map((content) => {
                                            return <div className="p-0 m-0" key={content.id}>
                                                <MediaCard
                                                    key={content.id}
                                                    id={content.id}
                                                    image={content.imageUrl}
                                                    title={content.title}
                                                    releaseDate={content.releaseDate.slice(0, 4)}
                                                    type={content.type.toLowerCase()}/>
                                            </div>
                                        })
                                    }
                                </ResponsiveMediaGrid>
                                <div className="flex justify-center mt-4">
                                    {
                                        (mediaPaginated.data.length > 0 && mediaPaginated.links.last.page > 1) &&
                                        <PaginationComponent page={page}
                                                             lastPage={mediaPaginated.links.last.page}
                                                             setPage={setPage}/>
                                    }
                                </div>
                            </> : <NoResults title={t('genre_no_media')}/>
                    }
                </div>
                : <Loader/>
        }
    </>);
}
