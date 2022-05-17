import {Tab, Tabs} from "@mui/material";
import {useTranslation} from "react-i18next";
import MediaCard from "../media/MediaCard";
import ListsCard from "../lists/ListsCard";
import PaginationComponent from "../PaginationComponent";
import NoResults from "./NoResults";
import ResponsiveMediaGrid from "../ResponsiveMediaGrid";

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
                        <ResponsiveMediaGrid>
                            {props.media.data.map((content) => {
                                return <div className="p-0 m-0"
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
                        </ResponsiveMediaGrid>
                        <div className="flex justify-center pt-1">
                            {(props.media.data.length > 0 && props.media.links.last.page > 1) &&
                                <PaginationComponent page={props.mediaPage} lastPage={props.media.links.last.page}
                                                     setPage={props.setMediaPage}/>
                            }
                        </div>
                    </> : <NoResults/>}
                </TabPanel>

                <TabPanel value={props.activeTab} index={1}>
                    {props.lists.data ? <>
                        <ResponsiveMediaGrid>
                            {props.lists.data.map((content) => {
                                return <div className="p-0 m-0"
                                            key={content.id}>
                                    <ListsCard id={content.id} key={content.id}
                                               mediaUrl={content.mediaUrl}
                                               listTitle={content.name}/></div>
                            })}
                        </ResponsiveMediaGrid>
                        <div className="flex justify-center pt-1">
                            {(props.lists.data.length > 0 && props.lists.links.last.page > 1) &&
                                <PaginationComponent page={props.listPage} lastPage={props.lists.links.last.page}
                                                     setPage={props.setListPage}/>
                            }
                        </div>
                    </> : <NoResults/>}
                </TabPanel>
            </div>
        </>);
}

export default SearchResults;