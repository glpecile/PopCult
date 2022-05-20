import {useContext, useState} from "react";
import DialogContent from '@mui/material/DialogContent';
import LocalDialogTitle from "./LocalDialogTitle";
import LocalDialog from "./LocalDialog";
import {useLocation, useNavigate} from "react-router-dom";
import AuthContext from "../../store/AuthContext";
import {Tooltip} from "@mui/material";
import {useTranslation} from "react-i18next";
import LengthProgress from "../comments/LengthProgress";

export default function FormDialog(props) {
    const {t} = useTranslation();
    const [open, setOpen] = useState(props.isOpened);
    const navigate = useNavigate();
    const context = useContext(AuthContext);
    const location = useLocation();
    const MIN_LENGTH = 1;
    const MAX_LENGTH = 1000;



    const handleState = () => {
        if (!context.isLoggedIn) {
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            })
        }
        setOpen(!open);
    };

    return (
        <div>
            <Tooltip title={props.tooltip} arrow>
                <button type='button' onClick={handleState} className={props.buttonClassName}>
                    {props.buttonIcon}
                    {props.buttonText}
                </button>
            </Tooltip>
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
                            <LengthProgress length={props.reportBody.length} max={MAX_LENGTH} text={t('length_count', {current: props.reportBody.length, max: MAX_LENGTH})}/>
                            <div className="flex justify-end">
                                <button type="submit" disabled={!props.reportBody || props.reportBody.length < MIN_LENGTH || props.reportBody.length > MAX_LENGTH} onClick={() => {
                                    setOpen(false);
                                }}
                                        className="btn btn-link text-amber-500 hover:text-amber-700 btn-rounded">{props.actionTitle}</button>
                            </div>
                        </form>
                    </div>
                </DialogContent>
            </LocalDialog>
        </div>
    );
}