import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import CollaborativeService from "../../../services/CollaborativeService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import {Avatar, Checkbox, Chip, FormControlLabel, FormGroup} from "@mui/material";
import Spinner from "../../animation/Spinner";
import OneButtonDialog from "../../modal/OneButtonDialog";
import NewListSearchInput from "../creation/NewListSearchInput";
import UserService from "../../../services/UserService";
import PaginationComponent from "../../PaginationComponent";
import NoResults from "../../search/NoResults";

const ListHandleCollaborators = (props) => {
    const {t} = useTranslation();
    const [showCollaborators, setShowCollaborators] = useState(() => new Map());
    const [searchResults, setSearchResults] = useState(undefined);
    const [page, setPage] = useState(1);

    const [term, setTerm] = useState('');
    const {setErrorStatusCode} = useErrorStatus();
    const pageSize = 10;

    useEffect(() => {
        async function getListCollaborators() {
            try {
                const collaborators = await CollaborativeService.getListCollaborators({
                    url: props.collaboratorsUrl,
                    pageSize,
                    page
                });
                collaborators.data.forEach((user) => {
                    setShowCollaborators(prev => new Map([...prev, [user.username, user]]));
                })
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }

        }

        getListCollaborators();
    }, [props.collaboratorsUrl, pageSize, setErrorStatusCode, page]);

    const handleAdd = (user) => {
        if (props.toAddCollaborators.has(user.username)) {
            const aux = new Map(props.toAddCollaborators);
            aux.delete(user.username);
            props.setToAddCollaborators(aux);
        } else {
            props.setToAddCollaborators(prev => new Map([...prev, [user.username, user]]));

        }

    }

    const handleDelete = (user) => {
        if (props.toAddCollaborators.has(user.username)) {
            const aux = new Map(props.toAddCollaborators);
            aux.delete(user.username);
            props.setToAddCollaborators(aux);
            const helper = new Map(showCollaborators);
            helper.delete(user.username);
            setShowCollaborators(helper);
        } else {
            props.setToRemoveCollaborators(prev => new Map([...prev, [user.username, user]]));
        }
    }
    const undoDelete = (user) => {
        const aux = new Map(props.toRemoveCollaborators);
        aux.delete(user.username);
        props.setToRemoveCollaborators(aux);
    }

    const showChip = (user) => {
        return !props.toRemoveCollaborators.has(user.username);
    }

    const getSearchTerm = (searchTerm) => {
        // if (searchTerm.localeCompare(props.toSearch) === 0 && props.toSearch.length !== 0) props.setOpenModal(true);
        setTerm(searchTerm);
    }

    useEffect(() => {
        async function searchUsers(term) {
            try {
                const data = await UserService.getUsers({query: term, notCollabInList: props.id, page, pageSize})
                setSearchResults(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (term !== '') {
            searchUsers(term);
        }
    }, [setErrorStatusCode, term, page, pageSize, props.id]);


    const handleCollaboratorsChip = (user) => {
        return (<Checkbox checked={props.toAddCollaborators.has(user.username)} onChange={() => {
            handleAdd(user)
        }} color="secondary"/>);
    }

    const submitCollaborators = () => {
        setShowCollaborators(prevState => new Map([...prevState, ...props.toAddCollaborators]));
    }


    const ShowSearchResults = () => {
        return (<>
            <NewListSearchInput getSearchTerm={getSearchTerm}/>
            <div className="text-xl m-2">
                {t('list_collaborators_to_add')}
            </div>
            {searchResults ?
                <div className="flex flex-col">
                    {searchResults.data.length > 0 ?
                        <>
                            <FormGroup>
                                {searchResults.data.map(user => {
                                    return <FormControlLabel
                                        control={handleCollaboratorsChip(user)} label={user.username}
                                        key={user.username}/>
                                })}
                            </FormGroup>
                            <div className="flex justify-center">
                                {(searchResults.links.last.page > 1) &&
                                    <PaginationComponent page={page} lastPage={searchResults.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </> :
                        <div className="text-gray-400 flex justify-center">
                            <NoResults/>
                        </div>}
                </div> :
                <div className="text-gray-400 flex justify-center">Look for collaborators to add to your list!</div>}
        </>);
    }

    return (
        <>
            <div className="flex justify-start my-2 items-center">
                <div className="font-semibold text-xl pr-2">
                    {t('list_collaborators')}
                </div>
                <OneButtonDialog
                    buttonClassName="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded h-min m-0 p-0 flex items-center"
                    buttonText={t('lists_add_collab')}
                    title={t('lists_add_collab')}
                    body={<ShowSearchResults/>}
                    actionTitle={t('lists_dialog_done')}
                    onActionAccepted={submitCollaborators}
                    isOpened={false}/>
            </div>
            {showCollaborators ?
                <>
                    {(Array.from(showCollaborators.values()).length !== 0 && Array.from(showCollaborators.values()).length !== props.toRemoveCollaborators.size) ?
                        <div className="flex flex-row space-x-1">
                            {Array.from(showCollaborators.values()).map(user => {
                                return <div key={user.username}>
                                    {showChip(user) &&
                                        <Chip
                                            label={user.username}
                                            variant="outlined" color="secondary"
                                            className="ring-1 ring-violet-500"
                                            onDelete={() => handleDelete(user)}
                                            avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                                            key={user.username}/>
                                    }
                                </div>
                            })}
                        </div> :
                        <div className="text-gray-400 flex justify-center py-2">
                            {t('lists_noCollaborators')}
                        </div>}
                    {props.toRemoveCollaborators.size > 0 &&
                        <>
                            <div className="font-semibold text-xl p-2">
                                {t('list_collaborators_to_remove')}
                            </div>
                            <div className="flex flex-row space-x-1">
                                {Array.from(props.toRemoveCollaborators.values()).map(user => {
                                    return <Chip
                                        label={user.username}
                                        variant="outlined" color="secondary"
                                        className="ring-1 ring-violet-500"
                                        onDelete={() => undoDelete(user)}
                                        avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                                        key={user.username}/>

                                })}
                            </div>
                        </>
                    }
                </>
                : <Spinner/>}
        </>
    );
}
export default ListHandleCollaborators;