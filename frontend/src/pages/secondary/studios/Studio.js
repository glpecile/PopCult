import {useParams, useSearchParams} from "react-router-dom";
import useErrorStatus from "../../../hooks/useErrorStatus";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import StudioService from "../../../services/StudioService";
import MediaService from "../../../services/MediaService";
import NoResults from "../../../components/search/NoResults";
import ResponsiveMediaGrid from "../../../components/ResponsiveMediaGrid";
import MediaCard from "../../../components/media/MediaCard";
import PaginationComponent from "../../../components/PaginationComponent";
import Loader from "../errors/Loader";


export default function Studio() {
    const {id} = useParams();
    const {setErrorStatusCode} = useErrorStatus();
    const [searchParams] = useSearchParams();
    const {t} = useTranslation();

    const [studioData, setStudioData] = useState(undefined);
    const [mediaPaginated, setMediaPaginated] = useState(undefined);

    const [page, setPage] = useState(searchParams.get("page") || 1);
    const [pageSize] = useState(12);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const studio = await StudioService.getStudio(id);
                const mediaFromStudio = await MediaService.getMediaByUrl({
                    url: studio.mediaUrl,
                    page: page,
                    pageSize: pageSize
                });
                setStudioData(studio);
                setMediaPaginated(mediaFromStudio);
            } catch (e) {
                setErrorStatusCode(e.response.status);
            }
        }
        fetchData()
    }, [id, page, pageSize, setErrorStatusCode])

    return (<>
        {
            (mediaPaginated !== undefined) ?
                <div>
                    <div className="flex flex-col lg:flex-row gap-4 space-y-3">
                        <div className="flex flex-col basis-1/3">
                            <img className="w-full object-center rounded-lg shadow-md" src={studioData.imageUrl}
                                 alt={studioData.name}/>
                        </div>
                        <div className="basis-3/4">
                            <h1 className="text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide">
                                {studioData.name}
                            </h1>
                        </div>
                    </div>

                    <h5 className="font-bold text-2xl py-2">
                        {t('studio_media')}
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
                                <div className="flex justify-center pt-4">
                                    {
                                        (mediaPaginated.data.length > 0 && mediaPaginated.links.last.page > 1) &&
                                        <PaginationComponent page={page}
                                                             lastPage={mediaPaginated.links.last.page}
                                                             setPage={setPage}/>
                                    }
                                </div>
                            </> : <NoResults title={t('studio_no_media')}/>
                    }
                </div>
                : <Loader/>
        }
    </>);
}