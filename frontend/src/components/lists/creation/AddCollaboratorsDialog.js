import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import LocalDialogTitle from "../../modal/LocalDialogTitle";
import LocalDialog from "../../modal/LocalDialog";
import {Checkbox, FormControlLabel, FormGroup} from "@mui/material";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import Spinner from "../../animation/Spinner";
import PaginationComponent from "../../PaginationComponent";

export default function AddCollaboratorsDialog(props) {
    const {t} = useTranslation();

    const [inList, setInList] = useState(props.alreadyInList);

    const handleCheckboxChange = (user) => {
        if (inList.has(user.username)) {
            const aux = new Map(inList);
            aux.delete(user.username);
            setInList(aux);
        } else setInList(prev => new Map([...prev, [user.username, user]]));
    };

    const handleState = () => {
        props.setOpenModal(false)
        props.setAlreadyInList(inList);
    };

    useEffect(() => {
        setInList(props.alreadyInList)
    }, [props.alreadyInList]);

    const isChecked = (user) => {
        if (!inList || inList.length === 0) return false;
        return inList.has(user.username);

    }
    const isOwner = (user) => {
        return user.username.localeCompare(props.ownerUsername) === 0;
    }

    const handleCollaboratorsChip = (user) => {
        return <>{isOwner(user) ? <Checkbox checked={true} disabled color="secondary"/> : <Checkbox checked={isChecked(user) || false} onChange={() => {
            handleCheckboxChange(user);
        }} color="secondary"/>}</>;
    }

    return (
        <div>
            <LocalDialog fullWidth onClose={handleState} aria-labelledby="customized-dialog-title" open={props.isOpened}>
                <LocalDialogTitle id="customized-dialog-title" onClose={handleState}>
                    {t('search_title', {term: props.searchTerm})}
                </LocalDialogTitle>
                <DialogContent dividers>
                    <div className="flex flex-col">
                        {props.searchUsers ? <>
                            <FormGroup>
                                {(props.searchUsers.data.length > 0) ?
                                    (props.searchUsers.data.map(user => {
                                        return <FormControlLabel
                                            control={handleCollaboratorsChip(user)} label={user.username}
                                            key={user.username}/>
                                    })) : (<div className="text-gray-400">
                                        {t('search_no_results')}
                                    </div>)}
                            </FormGroup>
                            <div className="flex justify-center">
                                {(props.searchUsers.data.length > 0 && props.searchUsers.links.last.page > 1) &&
                                    <PaginationComponent page={props.page} lastPage={props.searchUsers.links.last.page}
                                                         setPage={props.setPage}/>
                                }
                            </div>
                        </> : (<div className="flex justify-center"><Spinner/></div>)}
                    </div>
                </DialogContent>
                <DialogActions>
                    <button autoFocus onClick={handleState}
                            className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded">
                        {t('lists_dialog_done')}
                    </button>
                </DialogActions>
            </LocalDialog>
        </div>
    );
}