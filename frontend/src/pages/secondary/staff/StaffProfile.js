import {useParams, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Tab, Tabs} from "@mui/material";
import {a11yProps, TabPanel} from "../../../components/TabsComponent";
import {useTranslation} from "react-i18next";
import StaffService from "../../../services/StaffService";
import useErrorStatus from "../../../hooks/useErrorStatus";
import Loader from "../errors/Loader";
import ShareMenu from "../../../components/media/share/ShareMenu";
import MediaService from "../../../services/MediaService";
import * as React from "react";
import NoResults from "../../../components/search/NoResults";
import ResponsiveMediaGrid from "../../../components/ResponsiveMediaGrid";
import MediaCard from "../../../components/media/MediaCard";
import PaginationComponent from "../../../components/PaginationComponent";
import {Helmet} from "react-helmet-async";

export default function StaffProfile() {
    const {id: staffIdParam} = useParams();
    const [staffMember, setStaffMember] = useState(undefined);
    const [staffMediaAll, setStaffMediaAll] = useState(undefined);
    const [staffMediaDirector, setStaffMediaDirector] = useState(undefined);
    const [staffMediaActor, setStaffMediaActor] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();

    let tabStyle = "capitalize";
    const {t} = useTranslation();
    const [value, setValue] = useState(0);

    const [searchParams] = useSearchParams();
    const [page, setPage] = useState(searchParams.get("page") || 1);
    const [pageSize] = useState(12);

    useEffect(() => {
        const fetchStaffData = async () => {
            try {
                // fetch
                const staffMemberData = await StaffService.getStaffMember(staffIdParam);
                /**
                 * StaffMemberData:
                 * @type: Object.
                 * @properties: description, id, imageUrl, mediaActorUrl, mediaDirectorUrl, mediaUrl, name, url.
                 */
                const staffMediaDataPromise = MediaService.getMediaFromStaff({
                    url: staffMemberData.mediaUrl,
                    page: page,
                    pageSize: pageSize
                });
                const staffMediaDirectorDataPromise = MediaService.getDirectorMedia({
                    url: staffMemberData.mediaUrl,
                    page: page,
                    pageSize: pageSize
                });
                const staffMediaActorDataPromise = MediaService.getActorMedia({
                    url: staffMemberData.mediaUrl,
                    page: page,
                    pageSize: pageSize
                });
                // promise all
                const [staffMediaData, staffMediaDirectorData, staffMediaActorData] = await Promise.all([staffMediaDataPromise, staffMediaDirectorDataPromise, staffMediaActorDataPromise]);

                // set
                setStaffMember(staffMemberData);
                setStaffMediaAll(staffMediaData);
                setStaffMediaDirector(staffMediaDirectorData);
                setStaffMediaActor(staffMediaActorData);
            } catch (e) {
                setErrorStatusCode(e.response.status);
            }
        }
        fetchStaffData();
    }, [staffIdParam, setErrorStatusCode, page, pageSize])

    return (<>{
        (staffMember && staffMediaAll !== undefined) ?
            <div className="space-y-2">
                <Helmet>
                    <title>{staffMember.name} • PopCult</title>
                </Helmet>
                <div className="flex flex-col lg:flex-row gap-4">
                    <div className="flex flex-col basis-1/3">
                        <div className="shadow-md rounded-lg divide-y divide-slate-300 my-3 bg-white">
                            <img className="w-full object-center rounded-t-lg" alt={staffMember.name} src={staffMember.imageUrl}/>
                            <ShareMenu isOpened={false}/>
                        </div>
                    </div>
                    <div className="basis-3/4">
                        <h1 className="text-5xl font-black justify-start pt-2 break-words max-w-full tracking-wide">
                            {staffMember.name}
                        </h1>
                        <p className="lead tracking-tight text-justify">
                            {staffMember.description}
                        </p>
                    </div>
                </div>

                <div className="flex justify-center">
                    <Tabs value={value}
                          variant="scrollable"
                          allowScrollButtonsMobile
                          scrollButtons="auto"
                          onChange={(event, newValue) => {
                              setValue(newValue)
                          }}
                          textColor="secondary"
                          indicatorColor="secondary"
                          aria-label="tabs">
                        <Tab className={tabStyle} label={t('staff_all')} {...a11yProps(0)}/>
                        <Tab className={tabStyle} label={t('staff_director')} {...a11yProps(1)}/>
                        <Tab className={tabStyle} label={t('staff_actor')} {...a11yProps(2)}/>
                    </Tabs>
                </div>
                <TabPanel value={value} index={0}>
                    {
                        // All media tab
                        (staffMediaAll && staffMediaAll.data) ? <>
                            <ResponsiveMediaGrid>
                                {
                                    staffMediaAll.data.map((content) => {
                                            return <div className="p-0 m-0" key={content.id}>
                                                <MediaCard
                                                    key={content.id}
                                                    id={content.id}
                                                    image={content.imageUrl}
                                                    title={content.title}
                                                    releaseDate={content.releaseDate.slice(0, 4)}
                                                    type={content.type.toLowerCase()}/>
                                            </div>
                                        }
                                    )
                                }
                            </ResponsiveMediaGrid>
                            <div className="flex justify-center pt-4">
                                {
                                    (staffMediaAll.data.length > 0 && staffMediaAll.links.last.page > 1) &&
                                    <PaginationComponent page={page}
                                                         lastPage={staffMediaAll.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </> : <NoResults title={t('staff_no_results_all', {name: staffMember.name})}/>
                    }
                </TabPanel>
                <TabPanel value={value} index={1}>
                    {
                        // Directing Tab
                        (staffMediaDirector && staffMediaDirector.data) ? <>
                            <ResponsiveMediaGrid>
                                {
                                    staffMediaDirector.data.map((content) => {
                                            return <div className="p-0 m-0" key={content.id}>
                                                <MediaCard
                                                    key={content.id}
                                                    id={content.id}
                                                    image={content.imageUrl}
                                                    title={content.title}
                                                    releaseDate={content.releaseDate.slice(0, 4)}
                                                    type={content.type.toLowerCase()}/>
                                            </div>
                                        }
                                    )
                                }
                            </ResponsiveMediaGrid>
                            <div className="flex justify-center pt-4">
                                {
                                    (staffMediaDirector.data.length > 0 && staffMediaDirector.links.last.page > 1) &&
                                    <PaginationComponent page={page}
                                                         lastPage={staffMediaDirector.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </> : <NoResults title={t('staff_no_results_director', {name: staffMember.name})}/>
                    }
                </TabPanel>
                <TabPanel value={value} index={2}>
                    {
                        // Actor tab
                        (staffMediaActor && staffMediaActor.data) ? <>
                            <ResponsiveMediaGrid>
                                {
                                    staffMediaActor.data.map((content) => {
                                            return <div className="p-0 m-0" key={content.id}>
                                                <MediaCard
                                                    key={content.id}
                                                    id={content.id}
                                                    image={content.imageUrl}
                                                    title={content.title}
                                                    releaseDate={content.releaseDate.slice(0, 4)}
                                                    type={content.type.toLowerCase()}/>
                                            </div>
                                        }
                                    )
                                }
                            </ResponsiveMediaGrid>
                            <div className="flex justify-center pt-4">
                                {
                                    (staffMediaActor.data.length > 0 && staffMediaActor.links.last.page > 1) &&
                                    <PaginationComponent page={page}
                                                         lastPage={staffMediaActor.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </> : <NoResults title={t('staff_no_results_actor', {name: staffMember.name})}/>
                    }
                </TabPanel>
            </div> : <Loader/>
    }</>)
}