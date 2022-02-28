import {Helmet} from "react-helmet-async";
import IconCard from "../../../components/IconCard";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useParams} from "react-router-dom";

const UserPanel = () => {
    const {t} = useTranslation();
    let {username} = useParams();

    const [requestsView, setRequestsView] = useState(false);
    const [commentsView, setCommentsView] = useState(false);
    useEffect(() => {
        console.log(requestsView);
    }, [requestsView])
    return (<>
        <Helmet>
            user panel
        </Helmet>
        {/*Title*/}
        <h1 className="text-center display-5 fw-bolder py-4">
            {t('user_panel_title', {username: username})}
        </h1>
        {(!requestsView && !commentsView) && <>
            <div className="grid lg:grid-cols-2 grid-cols-1 gap-5">
                {/*Requests*/}
                <IconCard icon={<i className="fas fa-users text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>}
                          title={t('user_panel_requests')}
                          description={t('user_panel_requests_detail')}
                          onClick={() => {
                              setRequestsView(true);
                          }}/>
                {/*Comments*/}
                <IconCard
                    icon={<i className="fas fa-comment-dots text-purple-500 group-hover:text-purple-900 fa-2x mt-3"/>}
                    title={t('user_panel_comments')}
                    description={t('user_panel_comments_detail')}
                    onClick={() => {
                        setCommentsView(true);
                    }}/>
            </div>
        </>}
        {requestsView && (<>Requests</>)}
        {commentsView && (<>Comments</>)}
    </>);
}
export default UserPanel;