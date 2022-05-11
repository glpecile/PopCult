import {useState} from "react";
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import LocalDialogTitle from "./LocalDialogTitle";
import LocalDialog from "./LocalDialog";

export default function FormDialog(props) {
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
                    <div>
                        <form onSubmit={props.submitReport}>
                            <label className="py-2 text-semibold w-full after:ml-0.5 after:text-violet-400">
                                {props.body}
                            </label>
                            <textarea
                                className={"rounded w-full bg-gray-50"}
                                value={props.reportBody} onChange={props.handleReport}/>
                            <div className="flex justify-end">
                                <button type="submit" onClick={() => {
                                    setOpen(false);
                                }}
                                        className="text-amber-500 hover:text-amber-700">{props.actionTitle}</button>
                            </div>
                        </form>
                    </div>
                </DialogContent>
            </LocalDialog>
        </div>
    );
}