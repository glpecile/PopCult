import * as React from "react";
import {useTranslation} from "react-i18next";
import {Tab, Tabs} from "@mui/material";
import {useCallback, useEffect, useRef, useState} from "react";
import {useLocation} from "react-router-dom";
import ReportService from "../../../services/ReportService";
import Spinner from "../../../components/animation/Spinner";
import NothingToShow from "../../../components/admin/NothingToShow";

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

const Reports = () => {
    let tabStyle = "capitalize";
    const {t} = useTranslation();
    const [value, setValue] = useState(0);
    const query = new URLSearchParams(useLocation().search);
    const [page, setPage] = useState(query.get('page') || 1);
    const [pageSize, setPageSize] = useState(query.get('page-size') || 2);

    const [reportedLists, setReportedLists] = useState(undefined);
    const [reportedListComments, setReportedListComments] = useState(undefined);
    const [reportedMediaComments, setReportedMediaComments] = useState(undefined);

    const [refreshLists, setRefreshLists] = useState(false);
    const [refreshListComments, setRefreshListComments] = useState(false);
    const [refreshMediaComments, setRefreshMediaComments] = useState(false);

    const reportedListsMounted = useRef(true);
    const reportedListCommentsMounted = useRef(true);
    const reportedMediaCommentsMounted = useRef(true);

    const getReportedLists = useCallback(async () => {
        if (reportedListsMounted.current) {
            try {
                const lists = await ReportService.getListReports({page, pageSize});
                setReportedLists(lists);
                console.log(lists);
            } catch (error) {
                console.log(error);
            }
        }
    }, [page, pageSize]);

    const getReportedListsComments = useCallback(async () => {
        if (reportedListCommentsMounted.current) {
            try {
                const comments = await ReportService.getListCommentReports({page, pageSize});
                setReportedListComments(comments);
            } catch (error) {
                console.log(error);
            }
        }
    }, [page, pageSize]);

    const getReportedMediaComments = useCallback(async () => {
        if (reportedMediaCommentsMounted.current) {
            try {
                const comments = await ReportService.getMediaCommentReports({page, pageSize});
                setReportedMediaComments(comments);
            } catch (error) {
                console.log(error);
            }
        }
    }, [page, pageSize]);

    useEffect(() => {
        reportedListsMounted.current = true;
        getReportedLists();
        return () => {
            reportedListsMounted.current = false;
        }
    }, [getReportedLists]);

    useEffect(() => {
        reportedListCommentsMounted.current = true;
        getReportedListsComments();
        return () => {
            reportedListCommentsMounted.current = false;
        }
    }, [getReportedListsComments]);

    useEffect(() => {
        reportedMediaCommentsMounted.current = true;
        getReportedMediaComments();
        return () => {
            reportedMediaCommentsMounted.current = false;
        }
    }, [getReportedMediaComments]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (<>
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
            {reportedLists === undefined && <Spinner/>}
            {reportedLists !== undefined && reportedLists.data.length === 0 &&
                <NothingToShow text={t('reports_lists_empty')}/>}
        </TabPanel>

        <TabPanel value={value} index={1}>
            {reportedListComments === undefined && <Spinner/>}
            {reportedListComments !== undefined && reportedListComments.data.length === 0 &&
                <NothingToShow text={t('reports_lists_comments_empty')}/>}
        </TabPanel>

        <TabPanel value={value} index={2}>
            {reportedMediaComments === undefined && <Spinner/>}
            {reportedMediaComments !== undefined && reportedMediaComments.data.length === 0 &&
                <NothingToShow text={t('reports_media_comments_empty')}/>}
        </TabPanel>
    </>);

}
export default Reports;