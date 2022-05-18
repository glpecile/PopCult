import * as React from "react";
import {useTranslation} from "react-i18next";
import {Tab, Tabs} from "@mui/material";
import {useEffect, useRef, useState} from "react";
import {Roles} from "../../../enums/Roles";
import ReportService from "../../../services/ReportService";
import Spinner from "../../../components/animation/Spinner";
import NothingToShow from "../../../components/admin/NothingToShow";
import RolesGate from "../../../components/permissions/RolesGate";
import ListReport from "../../../components/admin/ListReport";
import useErrorStatus from "../../../hooks/useErrorStatus";
import PaginationComponent from "../../../components/PaginationComponent";
import ListCommentReport from "../../../components/admin/ListCommentReport";
import {a11yProps, TabPanel} from "../../../components/TabsComponent";
import MediaCommentReport from "../../../components/admin/MediaCommentReport";

const Reports = () => {
    let tabStyle = "capitalize";
    const {t} = useTranslation();
    const [value, setValue] = useState(0);
    // const query = new URLSearchParams(useLocation().search);
    const [listsReportsPage, setListsReportsPage] = useState(1);
    const [listsCommentsReportsPage, setListsCommentsReportsPage] = useState(1);
    const [mediaCommentsReportsPage, setMediaCommentsReportsPage] = useState(1);

    const [reportedLists, setReportedLists] = useState(undefined);
    const [reportedListComments, setReportedListComments] = useState(undefined);
    const [reportedMediaComments, setReportedMediaComments] = useState(undefined);

    const [refreshLists, setRefreshLists] = useState(false);
    const [refreshListComments, setRefreshListComments] = useState(false);
    const [refreshMediaComments, setRefreshMediaComments] = useState(false);

    const reportedListsMounted = useRef(true);
    const reportedListCommentsMounted = useRef(true);
    const reportedMediaCommentsMounted = useRef(true);

    const {setErrorStatusCode} = useErrorStatus();


    useEffect(() => {
        reportedListsMounted.current = true;

        const getReportedLists = async () => {
            if (reportedListsMounted.current) {
                try {
                    const lists = await ReportService.getListReports({page: listsReportsPage, pageSize: 12});
                    setReportedLists(lists);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }
        }

        getReportedLists();
        return () => {
            reportedListsMounted.current = false;
        }
    }, [listsReportsPage, setErrorStatusCode, refreshLists]);

    useEffect(() => {
        reportedListCommentsMounted.current = true;

        const getReportedListsComments = async () => {
            if (reportedListCommentsMounted.current) {
                try {
                    const comments = await ReportService.getListCommentReports({
                        page: listsCommentsReportsPage,
                        pageSize: 12
                    });
                    setReportedListComments(comments);
                    console.log(comments);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }
        }

        getReportedListsComments();
        return () => {
            reportedListCommentsMounted.current = false;
        }
    }, [listsCommentsReportsPage, setErrorStatusCode, refreshListComments]);

    useEffect(() => {
        reportedMediaCommentsMounted.current = true;
        const getReportedMediaComments = async () => {
            if (reportedMediaCommentsMounted.current) {
                try {
                    const comments = await ReportService.getMediaCommentReports({
                        page: mediaCommentsReportsPage,
                        pageSize: 12
                    });
                    console.log(comments)
                    setReportedMediaComments(comments);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }
        }

        getReportedMediaComments();
        return () => {
            reportedMediaCommentsMounted.current = false;
        }
    }, [mediaCommentsReportsPage, setErrorStatusCode, refreshMediaComments]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (<RolesGate level={Roles.MOD}>
        <h1 className="text-3xl fw-bolder fw-bolder py-4 text-center">
            {t('reports')}
        </h1>
        <div className="flex justify-center">
            <Tabs value={value}
                  onChange={handleChange}
                  textColor="secondary"
                  indicatorColor="secondary"
                  aria-label="tabs">
                <Tab className={tabStyle} label={t('reports_list')} {...a11yProps(0)}/>
                <Tab className={tabStyle} label={t('reports_lists_comments')} {...a11yProps(1)}/>
                <Tab className={tabStyle} label={t('reports_media_comments')} {...a11yProps(2)}/>
            </Tabs>
        </div>

        <TabPanel value={value} index={0}>
            {!reportedLists ? <Spinner/> : reportedLists.data.length === 0 ?
                <NothingToShow text={t('reports_lists_empty')}/> :
                <>
                    {reportedLists.data.map((report) => {
                        return <ListReport key={report.url} url={report.url} reporterUsername={report.reporterUsername}
                                           reportedUsername={report.reportedUsername}
                                           listname={"\"" + report.reportedListName + "\""}
                                           reportBody={report.report}
                                           refresh={() => setRefreshLists(prev => prev + 1)}
                                           listId={report.reportedListUrl.split('/').pop()}/>
                    })}
                    <div className="flex justify-center pt-4">
                        {(reportedLists.data.length > 0 && reportedLists.links.last.page > 1) &&
                            <PaginationComponent page={listsReportsPage} lastPage={reportedLists.links.last.page}
                                                 setPage={setListsReportsPage}/>
                        }
                    </div>
                </>}
        </TabPanel>

        <TabPanel value={value} index={1}>
            {!reportedListComments ? <Spinner/> :
                <>{reportedListComments.data.length === 0 ?
                    <NothingToShow text={t('reports_lists_comments_empty')}/> :
                    <>
                        {reportedListComments.data.map((report) => {
                                return <ListCommentReport key={report.url} url={report.url} comment={report.reportedComment}
                                                          reporterUsername={report.reporterUsername}
                                                          reportedUsername={report.reportedUsername}
                                                          listId={report.listUrl.split('/').pop()}
                                                          listname={report.listName} reportBody={report.report}
                                                          refresh={() => setRefreshListComments(prev => prev + 1)}/>;
                            }
                        )}
                        <div className="flex justify-center pt-4">
                            {(reportedListComments.data.length > 0 && reportedListComments.links.last.page > 1) &&
                                <PaginationComponent page={listsCommentsReportsPage}
                                                     lastPage={reportedListComments.links.last.page}
                                                     setPage={setListsCommentsReportsPage}/>
                            }
                        </div>
                    </>
                }
                </>}
        </TabPanel>

        <TabPanel value={value} index={2}>
            {reportedMediaComments ?
                <>
                    {!reportedMediaComments.data || reportedMediaComments.data.length === 0 ?
                        <NothingToShow text={t('reports_media_comments_empty')}/> :
                        <>
                            {reportedMediaComments.data.map((report) => {
                                return <MediaCommentReport key={report.url} url={report.url} comment={report.reportedComment}
                                                           reporterUsername={report.reporterUsername}
                                                           reportedUsername={report.reportedUsername}
                                                           mediaId={report.mediaUrl.split('/').pop()}
                                                           mediaName={report.mediaTitle} reportBody={report.report}
                                                           refresh={() => setRefreshMediaComments(prev => prev + 1)}/>
                            })}
                            <div className="flex justify-center pt-4">
                                {(reportedMediaComments.data.length > 0 && reportedMediaComments.links.last.page > 1) &&
                                    <PaginationComponent page={mediaCommentsReportsPage}
                                                         lastPage={reportedMediaComments.links.last.page}
                                                         setPage={setMediaCommentsReportsPage}/>
                                }
                            </div>
                        </>
                    }
                </>: <Spinner/>}
        </TabPanel>
    </RolesGate>);

}
export default Reports;