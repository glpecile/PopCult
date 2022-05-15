import {useContext, useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import ListService from "../../../services/ListService";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import MediaService from "../../../services/MediaService";
import ListAddMedia from "../../../components/lists/edition/ListAddMedia";
import ListHandleMedia from "../../../components/lists/edition/ListHandleMedia";
import ListEditDetails from "../../../components/lists/edition/ListEditDetails";
import {Divider} from "@mui/material";
import DoneAllIcon from '@mui/icons-material/DoneAll';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import {useTranslation} from "react-i18next";
import {Close} from "@mui/icons-material";
import OneButtonDialog from "../../../components/modal/OneButtonDialog";
import AuthContext from "../../../store/AuthContext";
import Loader from "../errors/Loader";

function ListsEdition() {
    const {t} = useTranslation();

    const authContext = useContext(AuthContext);
    const {id} = useParams();
    const [list, setList] = useState(undefined);
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

    const navigate = useNavigate();

    const pageSize = 4;

    useEffect(() => {
        async function getList(id) {
            try {
                const data = await ListService.getListById(id);
                setList(data);
                setListName(data.name)
                setListDescription(data.description);
                setIsPublic(data.visibility);
                setCollaborative(data.collaborative);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }
        getList(id);
    }, [id, setErrorStatusCode]);


    useEffect(() => {
        async function searchMedia(term) {
            try {
                const series = await MediaService.getSeries({
                    page: seriesPage, pageSize: pageSize, query: term, notInList: id
                });
                setSearchSeries(series);
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
    }, [filmsPage, id, seriesPage, setErrorStatusCode, toSearch]);

    const deleteList = async () => {
        await ListService.deleteList(list.url);
        navigate('/lists');
    }

    return (<>
        {list ? <>
            <div>
                <ListEditDetails listName={listName} setListName={setListName} listDescription={listDescription}
                                 setListDescription={setListDescription} isCollaborative={collaborative}
                                 setCollaborative={setCollaborative}
                                 isPublic={isPublic} setIsPublic={setIsPublic}/>
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
                    {(authContext.isLoggedIn && authContext.username.localeCompare(list.owner) === 0) &&
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
                <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline">
                    <DoneAllIcon className="mb-1 mr-1"/>{t('save_changes')}
                </button>
            </div>
        </> : <Loader/>}</>);
}

export default ListsEdition;