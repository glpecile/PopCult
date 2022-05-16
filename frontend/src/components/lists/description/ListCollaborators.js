import {Avatar, Chip} from "@mui/material";
import {useEffect, useState} from "react";
import CollaborativeService from "../../../services/CollaborativeService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import {useTranslation} from "react-i18next";

const ListCollaborators = (props) => {
    const [collaboratorsInlist, setCollaboratorsInList] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();
    const {t} = useTranslation();

    const pageSize = 10;

    useEffect(() => {
        async function getListCollaborators() {
            try {
                const collaborators = await CollaborativeService.getListCollaborators({
                    url: props.collaboratorsUrl,
                    pageSize
                });
                setCollaboratorsInList(collaborators);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }

        }

        getListCollaborators();
    }, [props.collaboratorsUrl, pageSize, setErrorStatusCode])

    return <>{(collaboratorsInlist && collaboratorsInlist.data.length > 0) && (<>
        {t('collaborators_in_list')}
        {collaboratorsInlist.data.map((user) => {
            return <Chip
                label={user.username}
                className="ring-1 ring-violet-500 mx-1"
                variant="outlined" color="secondary"
                avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                key={user.username}
            />
        })}
    </>)}</>;
}
export default ListCollaborators;