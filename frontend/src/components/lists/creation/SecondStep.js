import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import NewListSearchInput from "./NewListSearchInput";
import MediaService from "../../../services/MediaService";
import AddMediaDialog from "./AddMediaDialog";
import CompactMediaCard from "../../media/CompactMediaCard";

const SecondStep = (props) => {
    const {t} = useTranslation();
    const [toSearch, setToSearch] = useState('');
    const [searchFilms, setSearchFilms] = useState(undefined);
    const [searchSeries, setSearchSeries] = useState(undefined);
    const pageSize = 4;
    const [seriesPage, setSeriesPage] = useState(1);
    const [filmsPage, setFilmsPage] = useState(1);
    const [openModal, setOpenModal] = useState(false);

    useEffect(() => {
        async function searchMedia(term) {
            try {
                const series = await MediaService.getSeries({page: seriesPage, pageSize: pageSize, query: term});
                setSearchSeries(series);
                const films = await MediaService.getFilms({page: filmsPage, pageSize: pageSize, query: term});
                setSearchFilms(films);
            } catch (e) {
                console.log(e);
            }
        }

        if (toSearch !== '') {
            searchMedia(toSearch);
            setOpenModal(true);
        }

    }, [toSearch, seriesPage, filmsPage]);

    const getSearchTerm = (searchTerm) => {
        if (searchTerm.localeCompare(toSearch) === 0 && toSearch.length !== 0) setOpenModal(true);
        setToSearch(searchTerm);
    }

    return (<>
        <div className="px-5 pt-3 my-3 space-y-2 text-semibold w-full">
            {t('lists_add_media')}
            <NewListSearchInput getSearchTerm={getSearchTerm}/>
            {props.addedMedia.size > 0 &&
                <div>
                    <div className="flex flex-col">
                        {t('lists_already_media')}
                    </div>
                    {Array.from(props.addedMedia.values()).map(media => {
                        return <CompactMediaCard title={media.title}
                                                 releaseDate={media.releaseDate.slice(0,4)}
                                                 image={media.imageUrl} key={media.id}
                                                 canDelete={true}
                                                 deleteMedia={() => {
                                                     const aux = new Map(props.addedMedia);
                                                     aux.delete(media.id);
                                                     props.setAddedMedia(aux);
                                                 }}
                                                 className="my-1"/>
                    })}
                </div>
            }
        </div>
        <AddMediaDialog isOpened={openModal} setOpenModal={setOpenModal} searchTerm={toSearch}
                        searchSeries={searchSeries} searchFilms={searchFilms}
                        seriesPage={seriesPage} filmsPage={filmsPage}
                        setSeriesPage={setSeriesPage} setFilmsPage={setFilmsPage} alreadyInList={props.addedMedia}
                        setAlreadyInList={props.setAddedMedia}/>
    </>);
}
export default SecondStep;