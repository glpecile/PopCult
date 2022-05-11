import ShareMenu from "../../media/share/ShareMenu";
import {useContext, useEffect, useState} from "react";
import collaborativeService from "../../../services/CollaborativeService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import AuthContext from "../../../store/AuthContext";
import EditIcon from '@mui/icons-material/Edit';
import {useTranslation} from "react-i18next";
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import ContentCopyIcon from '@mui/icons-material/ContentCopy';

const ListLowerIcons = (props) => {
    const {setErrorStatusCode} = useErrorStatus();
    const username = useContext(AuthContext).username;
    const [isCollaborator, setIsCollaborator] = useState();
    const {t} = useTranslation();

    useEffect(() => {
        async function getIsCollaboratorInList() {
            try {
                await collaborativeService.isListCollaborator({id: props.id, username: username});
                setIsCollaborator(true);
            } catch (error) {
                setIsCollaborator(false);
            }
        }

        getIsCollaboratorInList();
    }, [setErrorStatusCode, props.id, username]);

    return (
        <div className="flex flex-wrap justify-start">
            <ShareMenu isOpened={false}/>
            {isCollaborator && <div className="flex justify-center py-2">
                <button className="btn btn-link text-purple-500 group hover:text-purple-900 btn-rounded">
                    <EditIcon/>{t('list_edit')}
                </button>
            </div>}
            {(!isCollaborator && props.collaborative) &&
                <div className="flex justify-center py-2">
                    <button className="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded">
                        <GroupAddIcon/><span className="pl-2">{t('lists_collaborate')}</span>
                    </button>
                </div>}
            {props.owner.localeCompare(username) &&
                <div className="flex justify-end py-2">
                        <button type="submit" className="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded">
                           <ContentCopyIcon/><span className="pl-2">{t('lists_fork')}</span>
                        </button>
                </div>}
        </div>);
}
export default ListLowerIcons;