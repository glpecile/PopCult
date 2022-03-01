import {useLocation} from "react-router-dom";
import {useCallback, useEffect, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import * as React from 'react';
import {Tab, Tabs} from "@mui/material";
import UserService from "../../../services/UserService";
import ModRequestService from "../../../services/ModRequestService";
import Spinner from "../../../components/animation/Spinner";
import NothingToShow from "../../../components/admin/NothingToShow";
import ModeratorCard from "../../../components/admin/ModeratorCard";
import ModeratorsRequest from "../../../components/admin/ModeratorsRequest";


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


const Moderators = () => {
    const query = new URLSearchParams(useLocation().search);
    const [requestsPage, setRequestsPage] = useState(query.get('page') || 1);
    const [requestsPageSize, setRequestsPageSize] = useState(query.get('page-size') || 2);
    const [moderatorsPage, setModeratorsPage] = useState(query.get('page') || 1);
    const [moderatorsPageSize, setModeratorsPageSize] = useState(query.get('page-size') || 2);
    const [moderatorsRequests, setModeratorsRequests] = useState(undefined);
    const [activeModerators, setActiveModerators] = useState(undefined);
    const [activeModeratorsRefresh, setActiveModeratorsRefresh] = useState(false);
    const [moderatorsRequestRefresh, setModeratorsRequestRefresh] = useState(false);
    const moderatorsUsersMounted = useRef(true);
    const moderatorsRequestMounted = useRef(true);
    const [value, setValue] = useState(0);
    let tabStyle = "capitalize";

    const getModerators = useCallback(async () => {
        if (moderatorsUsersMounted.current) {
            try {
                const mods = await UserService.getModerators({page: moderatorsPage, pageSize: moderatorsPageSize});
                setActiveModerators(mods);
            } catch (error) {
                console.log(error);
            }
        }
    }, [moderatorsPage, moderatorsPageSize]);

    const getModeratorRequests = useCallback(async () => {
        if (moderatorsRequestMounted.current) {
            try {
                const requests = await ModRequestService.getModRequests({
                    page: requestsPage,
                    pageSize: requestsPageSize
                });
                setModeratorsRequests(requests);
                console.log(requests)
            } catch (error) {
                console.log(error);
            }
        }
    }, [requestsPage, requestsPageSize]);

    const removeModerator = useCallback(async (username) => {
        try {
            await UserService.removeMod(username);
            setActiveModeratorsRefresh((prevState => !prevState));
        } catch (error) {
            console.log(error);
        }
    }, []);

    const rejectModerator = useCallback(async (id) => {
        try {
            await ModRequestService.rejectModRequest(id);
            setModeratorsRequestRefresh((prevState => !prevState));
        } catch (error) {
            console.log(error);
        }
    }, []);

    const acceptModerator = useCallback(async (id) => {
        try {
            await ModRequestService.promoteToMod(id);
            setModeratorsRequestRefresh((prevState => !prevState));
        } catch (error) {
            console.log(error);
        }
    }, []);


    useEffect(() => {
        moderatorsRequestMounted.current = true;
        getModeratorRequests();
        return () => {
            moderatorsRequestMounted.current = false;
        }

    }, [getModeratorRequests, moderatorsRequestRefresh]);

    useEffect(() => {
        moderatorsUsersMounted.current = true;
        getModerators();
        return () => {
            moderatorsUsersMounted.current = false;
        }

    }, [getModerators, activeModeratorsRefresh]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    const {t} = useTranslation();

    return (
        <>
            <h1 className="text-3xl fw-bolder fw-bolder py-4 text-center">
                {t('moderators')}
            </h1>
            <div className="flex justify-center">
                <Tabs value={value}
                      onChange={handleChange}
                      textColor="secondary"
                      indicatorColor="secondary"
                      aria-label="tabs">
                    <Tab className={tabStyle} label={t('moderators_active')} {...a11yProps(0)}/>
                    <Tab className={tabStyle} label={t('moderators_request')} {...a11yProps(1)}/>
                </Tabs>
            </div>
            <TabPanel value={value} index={0}>
                {activeModerators === undefined && <Spinner/>}
                {activeModerators !== undefined && activeModerators.data.length === 0 &&
                    <NothingToShow text={t('moderators_active_empty')}/>}
                {activeModerators !== undefined && activeModerators.data.length !== 0 && (activeModerators.data.map(user => {
                    return <ModeratorCard key={user.username} username={user.username}
                                          image={user.imageUrl}
                                          removeModerator={removeModerator}
                    />;
                }))}

            </TabPanel>

            <TabPanel value={value} index={1}>
                {moderatorsRequests === undefined && <Spinner/>}
                {moderatorsRequests !== undefined && moderatorsRequests.data.length === 0 &&
                    <NothingToShow text={t('moderators_request_empty')}/>}
                {moderatorsRequests !== undefined && moderatorsRequests.data.length !== 0 && (moderatorsRequests.data.map(user => {
                    return <ModeratorsRequest key={user.username} username={user.username}
                                          image={user.imageUrl}
                                          rejectModerator={rejectModerator}
                                          acceptModerator={acceptModerator}
                    />;
                }))}
            </TabPanel>
        </>);
}
export default Moderators;