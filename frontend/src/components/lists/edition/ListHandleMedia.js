import {Checkbox, FormControlLabel, FormGroup} from "@mui/material";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import CompactMediaCard from "../../media/CompactMediaCard";
import PaginationComponent from "../../PaginationComponent";

const ListHandleMedia = (props) => {
    const {t} = useTranslation();
    const [mediaInList, setMediaInList] = useState(undefined);
    const [page, setPage] = useState(1);
    const {setErrorStatusCode} = useErrorStatus();
    const pageSize = 4;

    const style = {
        "& .MuiFormControlLabel-root": {
            width: "100%",
        }, "& .MuiFormControlLabel-label": {
            width: "100%",
        },
    };

    useEffect(() => {
        async function getListMedia() {
            try {
                const media = await ListService.getMediaInList({url: props.list.mediaUrl, page: page, pageSize});
                setMediaInList(media);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getListMedia();
    }, [props.list, page, pageSize, setErrorStatusCode])

    const isChecked = (id) => {
        return !props.toRemoveMedia.has(id);
    }

    const handleChange = (media) => {
        if (props.toRemoveMedia.has(media.id)) {
            const aux = new Map(props.toRemoveMedia);
            aux.delete(media.id);
            props.setToRemoveMedia(aux);
        } else props.setToRemoveMedia(prev => new Map([...prev, [media.id, media]]));
    }

    return <>
        <div className="font-semibold text-xl">
            {t('list_already_media')}
        </div>
        <div>
            {mediaInList && mediaInList.data.length > 0 ? (<>
                <FormGroup>
                    {mediaInList.data.map((media) => {
                        return <FormControlLabel sx={style} control={<Checkbox checked={isChecked(media.id)} onChange={() => {handleChange(media)}} color="secondary"/>}
                                                 label={<CompactMediaCard canDelete={false} title={media.title}
                                                                          releaseDate={media.releaseDate.slice(0, 4)}
                                                                          image={media.imageUrl} className="mb-1"/>}
                                                 key={media.id} className="py-1"/>
                    })}
                </FormGroup>
                <div className="flex justify-center">
                    {(mediaInList.links.last.page > 1) &&
                        <PaginationComponent page={page} lastPage={mediaInList.links.last.page}
                                             setPage={setPage}/>
                    }
                </div>
            </>) : (<div className="text-gray-400 flex justify-center py-2">{t('lists_noMedia')}</div>)}
        </div>
    </>;
}
export default ListHandleMedia