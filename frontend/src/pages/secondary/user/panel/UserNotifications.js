import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import * as React from "react";
import {useContext, useEffect, useState} from "react";
import PaginationComponent from "../../../../components/PaginationComponent";
import NoResults from "../../../../components/search/NoResults";
import Spinner from "../../../../components/animation/Spinner";
import CommentNotification from "../../../../components/profile/panel/CommentNotification";
import UserContext from "../../../../store/UserContext";
import useErrorStatus from "../../../../hooks/useErrorStatus";
import notificationService from "../../../../services/NotificationService";

const UserNotifications = () => {
    const {t} = useTranslation();
    const [notifications, setNotifications] = useState(undefined);
    const [page, setPage] = useState(1);
    const [refresh, setRefresh] = useState(0);
    const user = useContext(UserContext).user;
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getNotifications() {
            try {
                const data = await notificationService.getUserNotifications({
                    url: user.notificationsUrl,
                    page,
                    pageSize: 12
                })
                setNotifications(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (user)
            getNotifications();
    }, [page, user, refresh, setErrorStatusCode]);

    const refreshRequests = () => {
        setRefresh(values => values + 1);
    }

    return (<>
        <Helmet>
            <title>{t('user_panel_comments')}</title>
        </Helmet>
        <h1 className="text-3xl fw-bolder fw-bolder py-4 text-center">
            {t('user_panel_comments')}
        </h1>

        {(notifications) ?
            <>
                {notifications.data && notifications.data.length > 0 ?
                    <>{notifications.data.map(notification => {
                        return <CommentNotification key={notification.url} url={notification.url}
                                                    username={notification.commentOwner} listname={notification.list}
                                                    listId={notification.listUrl.split('/').pop()}
                                                    refresh={refreshRequests}
                                                    commentBody={notification.comment} opened={notification.opened}/>
                    })}
                        <div className="flex justify-center pt-2">
                            {(notifications.links.last.page > 1) &&
                                <PaginationComponent page={page} lastPage={notifications.links.last.page}
                                                     setPage={setPage}/>
                            }
                        </div>
                    </>
                    :
                    <NoResults/>}
            </>
            :
            <Spinner/>}
    </>);
}
export default UserNotifications;