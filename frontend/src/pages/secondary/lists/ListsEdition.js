import {useCallback, useContext, useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import ListService from "../../../services/ListService";
import {useNavigate, useParams} from "react-router-dom";
import MediaService from "../../../services/MediaService";
import ListAddMedia from "../../../components/lists/edition/ListAddMedia";
import ListHandleMedia from "../../../components/lists/edition/ListHandleMedia";
import ListEditDetails from "../../../components/lists/edition/ListEditDetails";
import {Avatar, Checkbox, Chip, Divider, FormControlLabel} from "@mui/material";
import DoneAllIcon from '@mui/icons-material/DoneAll';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import {useTranslation} from "react-i18next";
import OneButtonDialog from "../../../components/modal/OneButtonDialog";
import AuthContext from "../../../store/AuthContext";
import Loader from "../errors/Loader";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import CompactMediaCard from "../../../components/media/CompactMediaCard";
import listService from "../../../services/ListService";
import ListHandleCollaborators from "../../../components/lists/edition/ListHandleCollaborators";
import collaborativeService from "../../../services/CollaborativeService";

function ListsEdition() {
    const {t} = useTranslation();

    const authContext = useContext(AuthContext);
    const {id} = useParams();
    const [list, setList] = useState(undefined);
    const [isOwner, setIsOwner] = useState(false);
    const [listName, setListName] = useState('');
    const [listDescription, setListDescription] = useState('');
    const [collaborative, setCollaborative] = useState(false);
    const [isPublic, setIsPublic] = useState(false);
    const [toSearch, setToSearch] = useState('');
    const {setErrorStatusCode} = useErrorStatus();
    const [toAddMedia, setToAddMedia] = useState(() => new Map());
    const [toRemoveMedia, setToRemoveMedia] = useState(() => new Map());
    const [searchFilms, setSearchFilms] = useState(undefined);
    const [searchSeries, setSearchSeries] = useState(undefined);
    const [openModal, setOpenModal] = useState(false);
    const [seriesPage, setSeriesPage] = useState(1);
    const [filmsPage, setFilmsPage] = useState(1);
    const [toAddCollaborators, setToAddCollaborators] = useState(() => new Map());
    const [toRemoveCollaborators, setToRemoveCollaborators] = useState(() => new Map());

    const navigate = useNavigate();
    const pageSize = 4;

    useEffect(() => {
        async function getList(id) {
            try {
                const data = await ListService.getListById(id);
                console.log(data);
                setList(data);
                setIsOwner(authContext.username.localeCompare(data.owner) === 0);
                setListName(data.name)
                setListDescription(data.description);
                setIsPublic(data.visibility);
                setCollaborative(data.collaborative);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getList(id);
    }, [id, setErrorStatusCode, authContext.username]);


    useEffect(() => {
        async function searchMedia(term) {
            try {
                const series = await MediaService.getSeries({
                    page: seriesPage, pageSize: pageSize, query: term, notInList: id
                });
                setSearchSeries(series);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (toSearch !== '') {
            searchMedia(toSearch);
        }
    }, [seriesPage, id, setErrorStatusCode, toSearch]);

    useEffect(() => {
        async function searchMedia(term) {
            try {
                const films = await MediaService.getFilms({
                    page: filmsPage, pageSize: pageSize, query: term, notInList: id
                });
                setSearchFilms(films);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (toSearch !== '') {
            searchMedia(toSearch);
            setOpenModal(true);
        }
    }, [filmsPage, id, setErrorStatusCode, toSearch]);

    const deleteList = async () => {
        await ListService.deleteList(list.url);
        navigate('/lists');
    }

    const ConfirmEditData = () => {
        const publicLabel = t('lists_isPublic');
        const collaborativeLabel = t('lists_isCollaborative');

        return <div className="space-y-2 text-semibold w-full">
            <div className="flex justify-center py-1">
                <CheckCircleOutlineIcon className="text-violet-500 mb-1 mr-1"/>
                {t('lists_verify')}
            </div>
            <Divider className="text-violet-500"/>
            <div className="flex flex-wrap pt-1">
                <h2 className="text-xl fw-bolder">
                    {listName}
                </h2>
            </div>
            <p className="font-thin text-base text-justify max-w-full break-words">
                {listDescription}
            </p>
            <div className="flex justify-between pt-1 px-2">
                <FormControlLabel control={<Checkbox checked={isPublic} color="secondary"/>} label={publicLabel}/>
                <FormControlLabel control={<Checkbox checked={collaborative} color="secondary"/>}
                                  label={collaborativeLabel}/>
            </div>

            <div className="flex flex-col py-1 font-semibold">
                {t('list_collaborators_new')}
            </div>
            {toAddCollaborators.size > 0 ? <div className="flex flex-row space-x-1">
                {Array.from(toAddCollaborators.values()).map(user => {
                    return <Chip
                        label={user.username}
                        variant="outlined" color="secondary"
                        className="ring-1 ring-violet-500"
                        avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                        key={user.username}/>

                })}
            </div> : <div className="text-gray-400 flex justify-center py-2">{t('list_new_collaborators_none')}</div>}
            <div className="flex flex-col py-1 font-semibold">
                {t('list_collaborators_to_remove')}
            </div>
            {toRemoveCollaborators.size > 0 ? <div className="flex flex-row space-x-1">
                {Array.from(toRemoveCollaborators.values()).map(user => {
                    return <Chip
                        label={user.username}
                        variant="outlined" color="secondary"
                        className="ring-1 ring-violet-500"
                        avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                        key={user.username}/>

                })}
            </div> : <div className="text-gray-400 flex justify-center py-2">{t('list_remove_collaborators_none')}</div>}

            <div className="flex flex-col py-1 font-semibold">
                {t('list_new_media')}
            </div>
            {toAddMedia.size > 0 ?
                <>
                    {Array.from(toAddMedia.values()).map((media) => {
                        return <CompactMediaCard canDelete={false} key={media.id}
                                                 title={media.title} releaseDate={media.releaseDate.slice(0, 4)}
                                                 image={media.imageUrl} className="py-1 mb-1"/>

                    })}
                </> : <div className="text-gray-400 flex justify-center py-2">{t('list_new_media_none')}</div>}

            <div className="flex flex-col py-1 font-semibold">
                {t('list_remove_media')}
            </div>
            {toRemoveMedia.size > 0 ?
                <>
                    {Array.from(toRemoveMedia.values()).map((media) => {
                        return <CompactMediaCard canDelete={false} key={media.id}
                                                 title={media.title} releaseDate={media.releaseDate.slice(0, 4)}
                                                 image={media.imageUrl} className="py-1 mb-1"/>

                    })}

                </> : <div className="text-gray-400 flex justify-center py-2">{t('list_remove_media_none')}</div>}
        </div>;
    }

    const manageDescription = useCallback(async () => {
        if (isOwner) {
            try {
                await listService.editList({
                    url: list.url,
                    title: listName,
                    description: listDescription,
                    isPublic: isPublic,
                    isCollaborative: collaborative
                });
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }
    }, [setErrorStatusCode, list, listName, listDescription, isPublic, collaborative, isOwner]);

    const manageMedia = useCallback(async () => {
        if (toAddMedia.size > 0 || toRemoveMedia.size > 0) {
            try {
                await listService.manageMediaInList({
                    url: list.mediaUrl,
                    add: Array.from(toAddMedia.keys()),
                    remove: Array.from(toRemoveMedia.keys())
                })
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }
    }, [list, setErrorStatusCode, toAddMedia, toRemoveMedia]);

    const manageCollaborators = useCallback(async () => {
        if (toRemoveCollaborators.size > 0 || toAddCollaborators.size > 0) {
            try {
                await collaborativeService.manageListCollaborators({
                    url: list.collaboratorsUrl,
                    add: Array.from(toAddCollaborators.keys()),
                    remove: Array.from(toRemoveCollaborators.keys())
                });
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }
    }, [list, toRemoveCollaborators, toAddCollaborators, setErrorStatusCode]);

    const saveListChanges = async () => {
        await Promise.all([manageDescription(), manageMedia(), manageCollaborators()]);
        navigate(`/lists/${list.id}`);
    }

    return (<>
        {list ? <>
            <div>
                <ListEditDetails listName={listName} setListName={setListName} listDescription={listDescription}
                                 setListDescription={setListDescription} isCollaborative={collaborative}
                                 setCollaborative={setCollaborative}
                                 isPublic={isPublic} setIsPublic={setIsPublic} isOwner={isOwner}/>
                {(authContext.isLoggedIn && isOwner) &&
                    <ListHandleCollaborators collaboratorsUrl={list.collaboratorsUrl}
                                             toAddCollaborators={toAddCollaborators}
                                             setToAddCollaborators={setToAddCollaborators}
                                             toRemoveCollaborators={toRemoveCollaborators}
                                             setToRemoveCollaborators={setToRemoveCollaborators} id={id}/>}
                <ListAddMedia openModal={openModal} setOpenModal={setOpenModal} toSearch={toSearch}
                              setToSearch={setToSearch}
                              searchSeries={searchSeries} searchFilms={searchFilms}
                              seriesPage={seriesPage} filmsPage={filmsPage}
                              setSeriesPage={setSeriesPage} setFilmsPage={setFilmsPage} toAddMedia={toAddMedia}
                              setToAddMedia={setToAddMedia}/>
                <Divider className="text-violet-500 m-3"/>
                <ListHandleMedia list={list} toRemoveMedia={toRemoveMedia} setToRemoveMedia={setToRemoveMedia}/>
            </div>
            <div className="flex justify-between mt-2">
                <div className="flex justify-start">
                    <button className="btn btn-link my-2.5 text-amber-500 hover:text-amber-700 btn-rounded outline mr-2"
                            onClick={() => {
                                navigate(`/lists/${list.id}`)
                            }}><ArrowBackIcon className="mb-1 mr-1"/>{t('discard_changes')}
                    </button>
                    {(authContext.isLoggedIn && isOwner) &&
                        <OneButtonDialog
                            buttonClassName="btn btn-link my-2.5 text-red-500 hover:text-red-700 btn-rounded outline mr-2"
                            buttonIcon={<DeleteForeverIcon className="mb-1 mr-1"/>}
                            buttonText={t('list_delete')}
                            title={t('list_delete')}
                            body={t('list_delete_body')}
                            actionTitle={t('list_delete_confirm')}
                            onActionAccepted={deleteList}
                            isOpened={false}/>}
                </div>
                <OneButtonDialog
                    buttonClassName="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline"
                    buttonIcon={<DoneAllIcon className="mb-1 mr-1"/>}
                    buttonText={t('save_changes')}
                    title={t('lists_edit')}
                    body={<ConfirmEditData/>}
                    actionTitle={t('save_changes')}
                    onActionAccepted={saveListChanges}
                    isOpened={false}/>
            </div>
        </> : <Loader/>}</>);
}

export default ListsEdition;