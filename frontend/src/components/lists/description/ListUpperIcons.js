import FavoriteButton from "../../FavoriteButton";
import FormDialog from "../../modal/FormDialog";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import favoriteService from "../../../services/FavoriteService";
import {useContext, useEffect, useState} from "react";
import AuthContext from "../../../store/AuthContext";
import {useLocation, useNavigate} from "react-router-dom";
import useErrorStatus from "../../../hooks/useErrorStatus";
import reportService from "../../../services/ReportService";
import {useTranslation} from "react-i18next";
import {Alert, Snackbar} from "@mui/material";

const ListUpperIcons = (list) => {
    const {t} = useTranslation();

    const [isLiked, setLiked] = useState(false);
    const [reportBody, setReportBody] = useState('');
    const context = useContext(AuthContext);
    const userIsLogged = context.isLoggedIn;
    const navigate = useNavigate();
    const location = useLocation();
    const [snackbar, setSnackbar] = useState(false);

    const {setErrorStatusCode} = useErrorStatus();

    async function handleLike() {
        try {
            async function handle() {
                if (!userIsLogged) {
                    navigate('/login', {
                        state: {
                            url: location.pathname
                        }
                    })
                } else {
                    try {
                        if (!isLiked) {
                            await favoriteService.addListToFavorites(list.favoriteUrl);
                        } else {
                            await favoriteService.removeListFromFavorites(list.favoriteUrl)
                        }
                        setLiked(!isLiked);
                    } catch (error) {
                        setErrorStatusCode(error.response.status);
                    }
                }
            }

            handle();
        } catch (error) {
            setLiked(false);
        }
        setLiked(!isLiked);
    }

    useEffect(() => {
        async function getIsFavorite() {
            try {
                try {
                    await favoriteService.isFavoriteMedia(list.favoriteUrl);
                    setLiked(true);
                } catch (error) {
                    setLiked(false);
                }
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (list) {
            getIsFavorite();
        }
    }, [list, setErrorStatusCode])

    function handleReport(event) {
        const aux = event.target.value;
        if (!(aux.includes('<') || aux.includes('>'))) setReportBody(aux);
    }

    useEffect(() => {
            const timeOut = setTimeout(() => {
                setSnackbar(false);
            }, 3000);
            return () => clearTimeout(timeOut);
        }
        , [snackbar]);

    async function submitReport(event) {
        event.preventDefault();
        try {
            const data = reportService.createListCommentReport({url: list.reportsUrl, data: reportBody});
            if (data.status === 204) {
                navigate('/lists', {
                    state: {
                        data: data.status
                    }
                });
            } else if (data.status === 201) {
                setSnackbar(true);
            }
        } catch (error) {
            setErrorStatusCode(error.response.status);
        }
    }

    return <div className="flex flex-grow justify-between items-center">
        <FavoriteButton isLiked={isLiked} handleLike={handleLike}/>
        {(context.isLoggedIn && context.username.localeCompare(list.owner) !== 0) && <FormDialog
            tooltip={t('report_content')}
            buttonClassName="text-amber-500 hover:text-amber-700 pt-2"
            buttonIcon={<ErrorOutlineIcon fontSize="large"/>}
            title={t('report_list_title')}
            body={t('report_list_body')}
            submitReport={submitReport}
            actionTitle={t('report_submit')}
            reportBody={reportBody}
            handleReport={handleReport}
            submitButtonClassName="text-amber-500 hover:text-amber-700"
            isOpened={false}/>}
        <Snackbar open={snackbar} autoHideDuration={6000}
                  anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
            <Alert severity="success">
                {t('report_success')}
            </Alert>
        </Snackbar>
    </div>;
}
export default ListUpperIcons;