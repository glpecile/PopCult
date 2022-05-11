import {IconButton, Slide} from "@mui/material";
import ContentCopyIcon from '@mui/icons-material/ContentCopy';
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {CopyToClipboard} from "react-copy-to-clipboard/src";

const ClipboardIcon = () => {
    const [alert, setAlert] = useState(false);
    const [t] = useTranslation();

    useEffect(() => {
        const timeOut = setTimeout(() => {
            setAlert(false);
        }, 1500)
        return () => clearTimeout(timeOut);
    }, [alert]);

    return (
        <>
            <div className="flex flex-col justify-center items-center">
                <CopyToClipboard text={window.location.href} onCopy={() => {
                    setAlert(true);
                }}>
                    <IconButton
                        className="rounded-full shadow-md w-20 h-20 flex items-center justify-center transition duration-300 ease-in-out transform hover:-translate-1 hover:scale-105 active:scale-90 bg-violet-400 hover:shadow-violet-400/50">
                        <ContentCopyIcon fontSize="large" className="text-white"/>
                    </IconButton>
                </CopyToClipboard>
                <p className="text-center py-2">
                    {t('media_share_clipboard')}
                </p>
            </div>
            <Slide direction="up" in={alert} mountOnEnter unmountOnExit>
                <div className="collapse show fixed bottom-0 -left-9 z-50" id="alert">
                    <div className="alert alert-secondary shadow-lg" role="alert">
                        {t('media_share_clipboard_message')}
                    </div>
                </div>
            </Slide>
        </>
    );
}

export default ClipboardIcon;