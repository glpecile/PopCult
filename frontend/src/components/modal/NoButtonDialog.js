import * as React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

export default function NoButtonDialog(props) {
    const [open, setOpen] = React.useState(props.isOpened);

    const handleState = () => {
        setOpen(!open);
    };

    return (
        <div>
            <button type='button' onClick={handleState} className={props.buttonClassName}>
                {props.buttonIcon}
                {props.buttonText}
            </button>
            <Dialog
                open={open}
                onClose={handleState}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {props.title}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {props.body}
                    </DialogContentText>
                </DialogContent>
            </Dialog>
        </div>
    );
}