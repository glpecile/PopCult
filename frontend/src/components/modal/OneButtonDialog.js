import * as React from 'react';
import {styled} from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Typography from '@mui/material/Typography';

const LocalDialog = styled(Dialog)(({theme}) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
}));

const LocalDialogTitle = (props) => {
    const {children, onClose, ...other} = props;

    return (
        <DialogTitle sx={{m: 0, p: 2}} {...other}>
            {children}
            {onClose ? (
                <IconButton
                    aria-label="close"
                    onClick={onClose}
                    sx={{
                        position: 'absolute',
                        right: 8,
                        top: 8,
                        color: (theme) => theme.palette.grey[500],
                    }}
                >
                    <CloseIcon/>
                </IconButton>
            ) : null}
        </DialogTitle>
    );
};

export default function OneButtonDialog(props) {
    const [open, setOpen] = React.useState(false);
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
                    }} className={props.buttonClassName}>
                        {props.actionTitle}
                    </button>
                </DialogActions>
            </LocalDialog>
        </div>
    );
}