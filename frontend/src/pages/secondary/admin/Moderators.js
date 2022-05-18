import {useCallback, useEffect, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import {Tab, Tabs} from "@mui/material";
import {a11yProps, TabPanel} from "../../../components/TabsComponent";
import * as React from 'react';
import UserService from "../../../services/UserService";
import ModRequestService from "../../../services/ModRequestService";
import Spinner from "../../../components/animation/Spinner";
import NothingToShow from "../../../components/admin/NothingToShow";
import ModeratorCard from "../../../components/admin/ModeratorCard";
import ModeratorsRequest from "../../../components/admin/ModeratorsRequest";
import RolesGate from "../../../components/permissions/RolesGate";
import {Roles} from "../../../enums/Roles";
import PaginationComponent from "../../../components/PaginationComponent";

const Moderators = () => {
    // const query = new URLSearchParams(useLocation().search);

    const [requestsPage, setRequestsPage] = useState(1);
    const [moderatorsPage, setModeratorsPage] = useState(1);

    const [moderatorsRequests, setModeratorsRequests] = useState(undefined);
    const [activeModerators, setActiveModerators] = useState(undefined);

    const [activeModeratorsRefresh, setActiveModeratorsRefresh] = useState(false);
    const [moderatorsRequestRefresh, setModeratorsRequestRefresh] = useState(false);

    const moderatorsUsersMounted = useRef(true);
    const moderatorsRequestMounted = useRef(true);

    const [value, setValue] = useState(0);
    let tabStyle = "capitalize";
    const {t} = useTranslation();


    const getModerators = useCallback(async () => {
        if (moderatorsUsersMounted.current) {
            try {
                const mods = await UserService.getModerators({page: moderatorsPage, pageSize: 12});
                setActiveModerators(mods);
            } catch (error) {
                console.log(error);
            }
        }
    }, [moderatorsPage]);

    const getModeratorRequests = useCallback(async () => {
        if (moderatorsRequestMounted.current) {
            try {
                const requests = await ModRequestService.getModRequests({
                    page: requestsPage, pageSize: 12
                });
                setModeratorsRequests(requests);
            } catch (error) {
                console.log(error);
            }
        }
    }, [requestsPage]);

    const removeModerator = useCallback(async (url) => {
        try {
            await UserService.removeMod(url);
            setActiveModeratorsRefresh((prevState => !prevState));
        } catch (error) {
            console.log(error);
        }
    }, []);

    const rejectModerator = useCallback(async (url) => {
        try {
            await ModRequestService.rejectModRequest(url);
            setModeratorsRequestRefresh((prevState => !prevState));
        } catch (error) {
            console.log(error);
        }
    }, []);

    const acceptModerator = useCallback(async (url) => {
        try {
            await ModRequestService.promoteToMod(url);
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

    }, [getModerators, activeModeratorsRefresh, moderatorsRequestRefresh]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (<RolesGate level={Roles.ADMIN}>
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
            {activeModerators === undefined ? <Spinner/> : activeModerators.data.length === 0 ? <NothingToShow
                text={t('moderators_active_empty')}/> : <>{activeModerators.data.length !== 0 && <>{(activeModerators.data.map(user => {
                return <ModeratorCard key={user.username} username={user.username}
                                      image={user.imageUrl}
                                      url={user.removeModUrl}
                                      removeModerator={removeModerator}
                />;
            }))}
                <div className="flex justify-center pt-4">
                    {(activeModerators.data.length > 0 && activeModerators.links.last.page > 1) &&
                        <PaginationComponent page={moderatorsPage}
                                             lastPage={activeModerators.links.last.page}
                                             setPage={setModeratorsPage}/>}
                </div>
            </>}
            </>}

        </TabPanel>

        <TabPanel value={value} index={1}>
            {moderatorsRequests === undefined ? <Spinner/> : moderatorsRequests.data.length === 0 ?
                <NothingToShow text={t('moderators_request_empty')}/> : <>{moderatorsRequests.data.length !== 0 && <>
                    {(moderatorsRequests.data.map(user => {
                        return <ModeratorsRequest key={user.username} username={user.username}
                                                  image={user.imageUrl}
                                                  url={user.url}
                                                  rejectModerator={rejectModerator}
                                                  acceptModerator={acceptModerator}
                        />;
                    }))}
                    <div className="flex justify-center pt-4">
                        {(moderatorsRequests.data.length > 0 && moderatorsRequests.links.last.page > 1) &&
                            <PaginationComponent page={requestsPage}
                                                 lastPage={moderatorsRequests.links.last.page}
                                                 setPage={setRequestsPage}/>}
                    </div>
                </>}</>}
        </TabPanel>
    </RolesGate>);
}
export default Moderators;