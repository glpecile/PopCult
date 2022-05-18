import PaginationComponent from "../PaginationComponent";
import * as React from 'react';
import DialogContent from '@mui/material/DialogContent';
import LocalDialog from "./LocalDialog";
import LocalDialogTitle from "./LocalDialogTitle";

export default function PaginatedDialog(props) {
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
            <LocalDialog
                open={open}
                onClose={handleState}
                aria-labelledby="alert-dialog-title"
            >
                <LocalDialogTitle id="alert-dialog-title">
                    {props.title}
                </LocalDialogTitle>
                <DialogContent dividers>
                    <div>
                        {props.body}
                    </div>
                    <div className="flex justify-center pt-4">
                        {(props.forks.links.last.page > 1) &&
                            <PaginationComponent page={props.page}
                                                 lastPage={props.forks.links.last.page}
                                                 setPage={props.setPage}/>
                        }
                    </div>
                </DialogContent>
            </LocalDialog>
        </div>
    );
}