import NewListSearchInput from "../creation/NewListSearchInput";
import AddMediaDialog from "../creation/AddMediaDialog";
import {Checkbox, FormControlLabel, FormGroup} from "@mui/material";
import CompactMediaCard from "../../media/CompactMediaCard";
import {useTranslation} from "react-i18next";

const ListAddMedia = (props) => {
    const {t} = useTranslation();

    const style = {
        "& .MuiFormControlLabel-root": {
            width: "100%",
        }, "& .MuiFormControlLabel-label": {
            width: "100%",
        },
    };

    const getSearchTerm = (searchTerm) => {
        if (searchTerm.localeCompare(props.toSearch) === 0 && props.toSearch.length !== 0) props.setOpenModal(true);
        props.setToSearch(searchTerm);
    }

    const handleCheckboxChange = (media) => {
        if (props.toAddMedia.has(media[0])) {
            const aux = new Map(props.toAddMedia);
            aux.delete(media[0]);
            props.setToAddMedia(aux);
        } else props.setToAddMedia(prev => new Map([...prev, [media[0], media[1]]]));
    };
    return (<>

            <div className="grid grid-cols-5 py-2 font-semibold text-xl w-full mt-2 flex items-center">
                <div>
                    {t('list_add_media')}
                </div>
                <div className="col-span-4 w-full">
                <NewListSearchInput getSearchTerm={getSearchTerm}/>
                </div>
            </div>

        <AddMediaDialog isOpened={props.openModal} setOpenModal={props.setOpenModal} searchTerm={props.toSearch}
                        searchSeries={props.searchSeries} searchFilms={props.searchFilms}
                        seriesPage={props.seriesPage} filmsPage={props.filmsPage}
                        setSeriesPage={props.setSeriesPage} setFilmsPage={props.setFilmsPage} alreadyInList={props.toAddMedia}
                        setAlreadyInList={props.setToAddMedia}/>
        <FormGroup>
            {(props.toAddMedia && Array.from(props.toAddMedia.entries()).length > 0) ? (Array.from(props.toAddMedia.entries()).map(media => {
                return <FormControlLabel sx={style}
                                         control={<Checkbox checked={true}
                                                            onChange={() => {
                                                                handleCheckboxChange(media);
                                                            }} color="secondary"/>}
                                         label={<CompactMediaCard canDelete={false} title={media[1].title}
                                                                  releaseDate={media[1].releaseDate.slice(0, 4)}
                                                                  image={media[1].imageUrl}
                                                                  className="mb-1"/>}
                                         key={media[1].id}
                                         className="py-1"/>
            })) : (<div className="text-gray-400 flex justify-center py-2">
                {t('list_no_media')}
            </div>)}
        </FormGroup></>);
}
export default ListAddMedia;