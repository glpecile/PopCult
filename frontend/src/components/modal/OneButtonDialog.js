import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Typography from '@mui/material/Typography';
import {useState} from "react";
import LocalDialogTitle from "./LocalDialogTitle";
import LocalDialog from "./LocalDialog";

export default function OneButtonDialog(props) {
    const [open, setOpen] = useState(props.isOpened);
    const handleState = () => {
        setOpen(!open);
    };

    return (
        <div>
            <button type='button' onClick={handleState} className={props.buttonClassName}>
                {props.buttonIcon}
                {props.buttonText}
            </button>
            <LocalDialog
                onClose={handleState}
                aria-labelledby="customized-dialog-title"
                open={open}
            >
                <LocalDialogTitle id="customized-dialog-title" onClose={handleState}>
                    {props.title}
                </LocalDialogTitle>
                <DialogContent dividers>
                    <Typography gutterBottom>
                        {props.body}
                    </Typography>
                </DialogContent>
                <DialogActions>
                    <button autoFocus onClick={() => {
                        handleState();
                        props.onActionAccepted();
                    }} className={props.submitButtonClassName || props.buttonClassName}>
                        {props.actionTitle}
                    </button>
                </DialogActions>
            </LocalDialog>
        </div>
    );
}