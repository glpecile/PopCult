import {Tab, Tabs} from "@mui/material";
import {useTranslation} from "react-i18next";
import MediaCard from "../media/MediaCard";
import ListsCard from "../lists/ListsCard";
import PaginationComponent from "../PaginationComponent";

function TabPanel(props) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`tabpanel-${index}`}
            aria-labelledby={`tab-${index}`}
            {...other}
        >
            {value === index && (
                <div className="p-3">
                    {children}
                </div>
            )}
        </div>
    );
}

function a11yProps(index) {
    return {
        id: `tab-${index}`,
        'aria-controls': `tabpanel-${index}`,
    };
}

const SearchResults = (props) => {
    const {t} = useTranslation();
    let tabStyle = "capitalize";

    const handleTabs = (event, newValue) => {
        props.setActiveTab(newValue);
    };
    return (
        <>
            <div className="flex justify-center w-full">
                <Tabs value={props.activeTab}
                      onChange={handleTabs}
                      textColor="secondary"
                      indicatorColor="secondary"
                      aria-label="tabs">
                    <Tab className={tabStyle} label={t('nav_media')} {...a11yProps(0)}/>
                    <Tab className={tabStyle} label={t('nav_lists')} {...a11yProps(1)}/>
                </Tabs>
            </div>
            <div className="flex flex-col">
                <TabPanel value={props.activeTab} index={0}>
                    {props.media.data ? <>
                        <div className="row py-2">
                            {props.media.data.map((content) => {
                                return <div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                                            key={content.id}>
                                    <MediaCard
                                        key={content.id}
                                        id={content.id}
                                        image={content.imageUrl}
                                        title={content.title}
                                        releaseDate={content.releaseDate.slice(0, 4)}
                                        type={content.type.toLowerCase()}
                                    /></div>
                            })}
                        </div>
                        <div className="flex justify-center pt-1">
                            {(props.media.data.length > 0 && props.media.links.last.page > 1) &&
                                <PaginationComponent page={props.mediaPage} lastPage={props.media.links.last.page}
                                                     setPage={props.setMediaPage}/>
                            }
                        </div>
                    </>: <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
                </TabPanel>

                <TabPanel value={props.activeTab} index={1}>
                    {props.lists.data ? <>
                        <div className="row py-2">
                            {props.lists.data.map((content) => {
                                return <div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                                            key={content.id}>
                                    <ListsCard id={content.id} key={content.id}
                                               mediaUrl={content.mediaUrl}
                                               listTitle={content.name}/></div>
                            })}
                        </div>
                        <div className="flex justify-center pt-1">
                            {(props.lists.data.length > 0 && props.lists.links.last.page > 1) &&
                                <PaginationComponent page={props.listPage} lastPage={props.lists.links.last.page}
                                                     setPage={props.setListPage}/>
                            }
                        </div>
                    </>: <div className="flex justify-center text-gray-400">{t('search_no_results')}</div>}
                </TabPanel>
            </div>
        </>);
}

export default SearchResults;