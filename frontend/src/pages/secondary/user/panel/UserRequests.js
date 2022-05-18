import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";
import * as React from "react";
import UserContext from "../../../../store/UserContext";
import {useContext, useEffect, useState} from "react";
import collaborativeService from "../../../../services/CollaborativeService";
import Spinner from "../../../../components/animation/Spinner";
import NoResults from "../../../../components/search/NoResults";
import CollaborationRequest from "../../../../components/profile/panel/CollaborationRequest";
import PaginationComponent from "../../../../components/PaginationComponent";
import useErrorStatus from "../../../../hooks/useErrorStatus";

const UserRequests = () => {
    const {t} = useTranslation();
    const user = useContext(UserContext).user;
    const [requests, setRequests] = useState(undefined);
    const [page, setPage] = useState(1);
    const [refresh, setRefresh] = useState(0);
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getRequests() {
            try {
                const data = await collaborativeService.getUserCollaborationRequests({
                    url: user.collabRequestsUrl,
                    page,
                    pageSize: 12
                })
                setRequests(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (user)
            getRequests();
    }, [page, user, refresh, setErrorStatusCode]);

    const refreshRequests = () => {
        setRefresh(values => values + 1);
    }

    return (<>
        <Helmet>
            <title>{t('user_panel_requests')}</title>
        </Helmet>
        <h1 className="text-3xl fw-bolder fw-bolder py-4 text-center">
            {t('user_panel_requests')}
        </h1>
        {(requests) ?
            <>
                {requests.data && requests.data.length > 0 ?
                    <>{requests.data.map(request => {
                        return <CollaborationRequest key={request.url} url={request.url} username={request.username} listname={request.list}
                        listId={request.listUrl.split('/').pop()} refresh={refreshRequests}/>
                    })}
                        <div className="flex justify-center pt-4">
                            {(requests.links.last.page > 1) &&
                                <PaginationComponent page={page} lastPage={requests.links.last.page}
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
export default UserRequests;