import {Avatar, Chip, Divider, Switch} from "@mui/material";
import NewListSearchInput from "./NewListSearchInput";
import {useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import {useTranslation} from "react-i18next";
import AddCollaboratorsDialog from "./AddCollaboratorsDialog";

const ThirdStep = (props) => {
    const {t} = useTranslation();

    const [toSearch, setToSearch] = useState('');
    const [searchUsers, setSearchUsers] = useState(undefined);
    const pageSize = 10;
    const [page, setPage] = useState(1);
    const [openModal, setOpenModal] = useState(false);

    useEffect(() => {
        async function searchUsers(term) {
            try {
                const data = await UserService.getUsers({page: page, pageSize: pageSize, query: term});
                setSearchUsers(data);
                setOpenModal(true);
            } catch (e) {
                console.log(e);
            }
        }

        if (toSearch !== '') {
            searchUsers(toSearch);
        }

    }, [toSearch, page]);

    const getSearchTerm = (searchTerm) => {
        if (searchTerm.localeCompare(toSearch) === 0 && toSearch.length !== 0) setOpenModal(true);
        setToSearch(searchTerm);
    }

    return (
        <div className="px-5 pt-3 my-3 space-y-2 text-semibold w-full">
            <div className="flex justify-between">
                {t('lists_public')}
                <div className="justify-end">
                    <Switch onClick={props.setPublic} color="secondary" checked={props.isPublic}/>
                </div>
            </div>
            <div className="flex justify-between">
                {t('lists_collaborative')}
                <div className="justify-end">
                    <Switch onClick={props.setCollaborative} color="secondary" checked={props.isCollaborative}/>
                </div>
            </div>
            {props.isCollaborative &&
                <div>
                    <Divider className="text-violet-500"/>
                    <div className="pt-3 text-semibold w-full flex flex-col">
                        {t('list_add_collab')}
                        <NewListSearchInput getSearchTerm={getSearchTerm}/>
                    </div>
                    {props.addedCollaborators.size > 0 &&
                        <div>
                            <div className="flex flex-col">
                                {t('list_already_collab')}
                            </div>
                            {Array.from(props.addedCollaborators.values()).map(user => {
                                return <Chip
                                    label={user.username}
                                    variant="outlined" color="secondary"
                                    avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                                    onDelete={() => {
                                        const aux = new Map(props.addedCollaborators);
                                        aux.delete(user.username);
                                        props.setAddedCollaborators(aux);
                                    }}
                                    key={user.username}
                                />
                            })}
                        </div>
                    }
                    <AddCollaboratorsDialog isOpened={openModal} setOpenModal={setOpenModal} searchUsers={searchUsers}
                                            alreadyInList={props.addedCollaborators}
                                            setAlreadyInList={props.setAddedCollaborators} setPage={setPage} page={page}
                                            searchTerm={toSearch}/>
                </div>}
        </div>
    );
}
export default ThirdStep;