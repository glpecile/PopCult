import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import LocalDialogTitle from "../../modal/LocalDialogTitle";
import LocalDialog from "../../modal/LocalDialog";
import {Checkbox, FormControlLabel, FormGroup, Tab, Tabs} from "@mui/material";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {a11yProps, TabPanel} from "../../TabsComponent";
import CompactMediaCard from "../../media/CompactMediaCard";
import Spinner from "../../animation/Spinner";
import PaginationComponent from "../../PaginationComponent";

export default function AddMediaDialog(props) {
    const {t} = useTranslation();
    const [inList, setInList] = useState(props.alreadyInList);
    const [tabValue, setTabValue] = useState(0);
    let tabStyle = "capitalize";

    const handleState = () => {
        props.setOpenModal(false)
        props.setAlreadyInList(inList);
    };
    const handleTabs = (event, newValue) => {
        setTabValue(newValue);
    };
    const handleCheckboxChange = (media) => {
        if (inList.has(media.id)) {
            const aux = new Map(inList);
            aux.delete(media.id);
            setInList(aux);
        } else setInList(prev => new Map([...prev, [media.id, media]]));
    };

    const isChecked = (media) => {
        if (!inList || inList.length === 0) return false;
        return inList.has(media.id);
    }

    useEffect(() => {
        setInList(props.alreadyInList)
    }, [props.alreadyInList]);

    const showCards = (searchMedia, page, isFilm) => {
        return (<>{searchMedia ? <>
            <FormGroup>
                {(searchMedia.data.length > 0) ?
                    (searchMedia.data.map(media => {
                        return <FormControlLabel sx={{
                            "& .MuiFormControlLabel-root": {
                                width: "100%",
                            },
                            "& .MuiFormControlLabel-label": {
                                width: "100%",
                            },
                        }}
                                                 control={<Checkbox checked={isChecked(media) || false}
                                                                    onChange={() => {
                                                                        handleCheckboxChange(media);
                                                                    }} color="secondary"/>}
                                                 label={<CompactMediaCard canDelete={false} title={media.title}
                                                                          releaseDate={media.releaseDate.slice(0, 4)}
                                                                          image={media.imageUrl} className="mb-1"/>}
                                                 key={media.id}
                                                 className="py-1"/>
                    })) : (<div className="text-gray-400 flex justify-center">
                        {t('search_no_results')}
                    </div>)}
            </FormGroup>
            <div className="flex justify-center pt-4">
                {(searchMedia.data.length > 0 && searchMedia.links.last.page > 1) && <> {
                    isFilm === true ?
                        (<PaginationComponent page={page} lastPage={searchMedia.links.last.page}
                                              setPage={props.setFilmsPage}/>) : (
                            <PaginationComponent page={page} lastPage={searchMedia.links.last.page}
                                                 setPage={props.setSeriesPage}/>)}</>}
            </div>
        </> : (<div className="flex justify-center"><Spinner/></div>)}
        </>);
    }
    return (
        <div>
            <LocalDialog fullWidth onClose={handleState} aria-labelledby="customized-dialog-title" open={props.isOpened}>
                <LocalDialogTitle id="customized-dialog-title" onClose={handleState}>
                    {t('search_title', {term: props.searchTerm})}
                </LocalDialogTitle>
                <DialogContent dividers>
                    <div className="flex justify-center w-full">
                        <Tabs value={tabValue}
                              variant="scrollable"
                              allowScrollButtonsMobile
                              scrollButtons="auto"
                              onChange={handleTabs}
                              textColor="secondary"
                              indicatorColor="secondary"
                              aria-label="tabs">
                            <Tab className={tabStyle} label={t('nav_films')} {...a11yProps(0)}/>
                            <Tab className={tabStyle} label={t('nav_series')} {...a11yProps(1)}/>
                        </Tabs>
                    </div>
                    <div className="flex flex-col">
                        <TabPanel value={tabValue} index={0}>
                            {showCards(props.searchFilms, props.filmsPage, true)}
                        </TabPanel>

                        <TabPanel value={tabValue} index={1}>
                            {showCards(props.searchSeries, props.seriesPage, false)}
                        </TabPanel>
                    </div>
                </DialogContent>
                <DialogActions>
                    <button autoFocus onClick={handleState}
                            className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded">
                        {t('lists_dialog_done')}
                    </button>
                </DialogActions>
            </LocalDialog>
        </div>
    );
}