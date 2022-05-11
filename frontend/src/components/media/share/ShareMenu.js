import ShareIcon from '@mui/icons-material/Share';
import {useState} from "react";
import {useTranslation} from "react-i18next";
import LocalDialogTitle from "../../modal/LocalDialogTitle";
import DialogContent from "@mui/material/DialogContent";
import LocalDialog from "../../modal/LocalDialog";
import WhatsappIcon from "./WhatsappIcon";
import ClipboardIcon from "./ClipboardIcon";
import TwitterIcon from "./TwitterIcon";

const ShareMenu = (props) => {
    const removeCookies = (originalURL) => {
        let trimmedURL = originalURL.substring(0, originalURL.indexOf("%3B"))
        return trimmedURL ? trimmedURL : originalURL;
    }
    let url = removeCookies(encodeURIComponent(window.location.href))
    const [open, setOpen] = useState(props.isOpened);
    const {t} = useTranslation();
    const handleState = () => {
        setOpen(!open);
    };
    return (
        <div>
            <div className="flex justify-center">
                <button type="button" onClick={handleState}
                        className="btn btn-link my-1.5 font-semibold text-violet-500 hover:text-violet-900 btn-rounded">
                    <ShareIcon/><span className="pl-2">{t('media_share_button')}</span>
                </button>
            </div>
            <LocalDialog
                onClose={handleState}
                aria-labelledby="customized-dialog-title"
                open={open}>
                <LocalDialogTitle id="customized-dialog-title" onClose={handleState}>
                    {t('media_share_header')}
                </LocalDialogTitle>
                <DialogContent dividers>
                    <div className="flex px-4 space-x-16">
                        <TwitterIcon url={url}/>
                        <WhatsappIcon url={url}/>
                        <ClipboardIcon/>
                    </div>
                </DialogContent>
            </LocalDialog>
        </div>
    )
}

export default ShareMenu;