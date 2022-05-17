import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Tab, Tabs} from "@mui/material";
import UserLists from "./tabs/UserLists";
import ListService from "../../services/ListService";
import MediaService from "../../services/MediaService";
import useErrorStatus from "../../hooks/useErrorStatus";
import UserMedia from "./tabs/UserMedia";

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

const UserTabs = (props) => {
    const {t} = useTranslation();
    const [value, setValue] = useState(0);
    let tabStyle = "capitalize";
    const {setErrorStatusCode} = useErrorStatus();

    const [userLists, setUserLists] = useState(undefined);
    const [userFavMedia, setUserFavMedia] = useState(undefined);
    const [userFavLists, setUserFavLists] = useState(undefined);
    const [userWatchedMedia, setUserWatchedMedia] = useState(undefined);
    const [userToWatchMedia, setUserToWatchMedia] = useState(undefined);
    const [userListsPage, setUserListsPage] = useState(1);
    const [userFavMediaPage, setFavMediaPage] = useState(1);
    const [userFavListsPage, setFavListsPage] = useState(1);
    const [userWatchedMediaPage, setWatchedMediaPage] = useState(1);
    const [userToWatchMediaPage, setToWatchMediaPage] = useState(1);
    const pageSize = 4;

    useEffect(() => {
        async function getUserLists() {
            try {
                if (props.currentUser) {
                    const data = await ListService.getMediaLists({
                        url: props.userData.listsUrl,
                        page: userListsPage,
                        pageSize
                    });
                    setUserLists(data);
                } else {
                    const data = await ListService.getMediaLists({
                        url: props.userData.publicListsUrl,
                        page: userListsPage,
                        pageSize
                    });
                    setUserLists(data);
                }
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getUserLists();
    }, [props, userListsPage, pageSize, setErrorStatusCode]);

    useEffect(() => {
        async function getUserLists() {
            try {
                if (props.currentUser) {
                    const data = await ListService.getMediaLists({
                        url: props.userData.favoriteListsUrl,
                        page: userFavListsPage,
                        pageSize
                    });
                    setUserFavLists(data);
                } else {
                    const data = await ListService.getMediaLists({
                        url: props.userData.publicFavoriteListsUrl,
                        page: userFavListsPage,
                        pageSize
                    });
                    setUserFavLists(data);
                }
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getUserLists();
    }, [props, userFavListsPage, pageSize, setErrorStatusCode]);

    useEffect(() => {
        async function getFavoriteMedia() {
            try {
                const data = await MediaService.getMediaByUrl({
                    url: props.userData.favoriteMediaUrl,
                    page: userFavMediaPage,
                    pageSize
                });
                setUserFavMedia(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getFavoriteMedia();
    }, [userFavMediaPage, props, pageSize, setErrorStatusCode]);

    useEffect(() => {
        async function getWatchedMedia() {
            try {
                const data = await MediaService.getMediaByUrl({
                    url: props.userData.watchedMediaUrl,
                    page: userWatchedMediaPage,
                    pageSize
                });

                setUserWatchedMedia(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getWatchedMedia();
    }, [userWatchedMediaPage, pageSize, props, setErrorStatusCode]);

    useEffect(() => {
        async function getToWatchMedia() {
            try {
                const data = await MediaService.getMediaByUrl({
                    url: props.userData.toWatchMediaUrl,
                    page: userToWatchMediaPage,
                    pageSize
                });

                setUserToWatchMedia(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getToWatchMedia();
    }, [props, pageSize, userToWatchMediaPage, setErrorStatusCode]);


    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <>
            <div className="flex justify-center">
                <Tabs value={value}
                      onChange={handleChange}
                      textColor="secondary"
                      indicatorColor="secondary"
                      aria-label="tabs">
                    <Tab className={tabStyle}
                         label={t('profile_tabs_main', {username: props.username})} {...a11yProps(0)}/>
                    <Tab className={tabStyle} label={t('profile_tabs_favMedia')} {...a11yProps(1)}/>
                    <Tab className={tabStyle} label={t('profile_tabs_favLists')} {...a11yProps(2)}/>
                    <Tab className={tabStyle} label={t('profile_tabs_watchedMedia')} {...a11yProps(3)}/>
                    <Tab className={tabStyle} label={t('profile_tabs_toWatchMedia')} {...a11yProps(4)}/>
                </Tabs>
            </div>
            <TabPanel value={value} index={0}>
                <UserLists setPage={setUserListsPage} lists={userLists} page={userListsPage}/>
            </TabPanel>

            <TabPanel value={value} index={1}>
                <UserMedia setPage={setFavMediaPage} media={userFavMedia} page={userFavMediaPage}/>
            </TabPanel>

            <TabPanel value={value} index={2}>
                <UserLists setPage={setFavListsPage} lists={userFavLists} page={userFavListsPage}/>
            </TabPanel>

            <TabPanel value={value} index={3}>
                <UserMedia setPage={setWatchedMediaPage} media={userWatchedMedia} page={userWatchedMediaPage}/>
            </TabPanel>

            <TabPanel value={value} index={4}>
                <UserMedia setPage={setToWatchMediaPage} media={userToWatchMedia} page={userToWatchMediaPage}/>
            </TabPanel>
        </>
    );
}
export default UserTabs;