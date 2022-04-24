import DialogTitle from "@mui/material/DialogTitle";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";

const LocalDialogTitle = (props) => {
    const {children, onClose, ...other} = props;

    return (
        <DialogTitle sx={{m: 0, p: 2}} {...other}>
            <div className="flex justify-between">
                <div className="pt-1">
                    {children}
                </div>
                {onClose ? (
                    <IconButton
                        aria-label="close"
                        onClick={onClose}
                        className="flex justify-end"
                    >
                        <CloseIcon/>
                    </IconButton>
                ) : null}
            </div>
        </DialogTitle>
    );
};

export default LocalDialogTitle;