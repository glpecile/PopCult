import {Divider, Pagination, Switch} from "@mui/material";
import NewListSearchInput from "./NewListSearchInput";
import Chips from "../../Chips";
import {useEffect, useState} from "react";
import UserService from "../../../services/UserService";
import {useTranslation} from "react-i18next";

const ThirdStep = (props) => {
    const {t} = useTranslation();

    const [toSearch, setToSearch] = useState('');
    const [searchUsers, setSearchUsers] = useState(undefined);
    const pageSize = 1;
    const [page, setPage] = useState(1);

    useEffect(() => {
        async function searchUsers(term) {
            try {
                const data = await UserService.getUsers({page: page, pageSize: pageSize, query: term});
                setSearchUsers(data);
            } catch (e) {
                console.log(e);
            }
        }
        if (toSearch !== '') searchUsers(toSearch);

    }, [toSearch, page]);

    const getSearchTerm = (searchTerm) => {
        setToSearch(searchTerm);
    }

    const handleChange = (event, value) => {
        setPage(value);
    };
    return (
        <div className="px-5 pt-3 my-3 space-y-2 text-semibold w-full">
            <div className="flex justify-between">
                {t('lists_public')}
                <div className="justify-end">
                    <Switch onClick={props.setPublic} color="secondary"/>
                </div>
            </div>
            <div className="flex justify-between">
                {t('lists_collaborative')}
                <div className="justify-end">
                    <Switch onClick={props.setCollaborative} color="secondary"/>
                </div>
            </div>
            {props.isCollaborative &&
                <div>
                    <Divider className="text-violet-500"/>
                    <div className="pt-3 text-semibold w-full flex flex-col">
                        Add collaborators to your list!
                        <NewListSearchInput getSearchTerm={getSearchTerm}/>
                    </div>
                    {props.addedCollaborators.length > 0 &&
                        <div>Already Collaborating:
                            {props.addedCollaborators.map(user => {
                                return <Chips url={user.url} text={user.username}/>;
                            })}
                        </div>}
                    {searchUsers &&
                        <Pagination count={parseInt(searchUsers.links.last.page)} variant="outlined" color="secondary"
                                    onChange={handleChange}/>}
                    {searchUsers && searchUsers.data.map(user => user.username)}
                </div>}
        </div>
    );
}
export default ThirdStep;