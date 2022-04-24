import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import LocalDialogTitle from "../../modal/LocalDialogTitle";
import LocalDialog from "../../modal/LocalDialog";
import {Checkbox, FormControlLabel, FormGroup, Pagination} from "@mui/material";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";

export default function AddCollaboratorsDialog(props) {
    const {t} = useTranslation();

    const [inList, setInList] = useState(props.alreadyInList);

    const handleChange = (event, value) => {
        props.setPage(value);
    };

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
    return (
        <div>
            <LocalDialog onClose={handleState} aria-labelledby="customized-dialog-title" open={props.isOpened}>
                <LocalDialogTitle id="customized-dialog-title" onClose={handleState}>
                    {t('lists_collabDialog_title')}
                </LocalDialogTitle>
                <DialogContent dividers>
                    <div className="flex flex-col">
                        <FormGroup>
                            {props.searchUsers && props.searchUsers.data.map(user => {
                                return <FormControlLabel
                                    control={<Checkbox checked={isChecked(user) || false} onChange={() => {
                                        handleCheckboxChange(user);
                                    }} color="secondary"/>} label={user.username} key={user.username}/>
                            })}
                        </FormGroup>
                        <div className="flex justify-center">
                        {(props.searchUsers && props.searchUsers.links.last.page > 1) &&
                            <Pagination count={parseInt(props.searchUsers.links.last.page)} variant="outlined"
                                        color="secondary"
                                        page={props.page}
                                        onChange={handleChange}/>}
                        </div>
                    </div>
                </DialogContent>
                <DialogActions>
                    <button autoFocus onClick={handleState}
                            className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded">
                        {t('list_dialog_done')}
                    </button>
                </DialogActions>
            </LocalDialog>
        </div>
    );
}