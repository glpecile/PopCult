import {useEffect, useState} from "react";
import CommentService from "../../services/CommentService";
import CommentComponent from "./CommentComponent";
import {List} from "@mui/material";
import {useTranslation} from "react-i18next";
import useErrorStatus from "../../hooks/useErrorStatus";
import {CommentSectionType} from "../../enums/CommentSectionType";
import NoResults from "../search/NoResults";

const CommentList = (props) => {
    const {t} = useTranslation();

    const pageSize = 5;
    const [page, setPage] = useState(1);
    const [maxPage, setMaxPage] = useState(1);
    const [comments, setComments] = useState([])
    const [links, setLinks] = useState(undefined);
    const [update, setUpdate] = useState(0);
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        if (props.commentsUpdate !== 0) {
            setPage(1);
            setUpdate(prev => prev + 1);
        }
    }, [props.commentsUpdate]);

    useEffect(() => {
        async function getComments() {
            try {
                let commentsList;
                if (props.type === CommentSectionType.LISTS) {
                    commentsList = await CommentService.getListComments({
                        url: props.commentsUrl,
                        page: page,
                        pageSize: pageSize
                    });
                } else {
                    commentsList = await CommentService.getMediaComments({
                        url: props.commentsUrl,
                        page: page,
                        pageSize: pageSize
                    });
                }
                if (page !== 1) {
                    setComments(prevState => [...prevState, ...commentsList.data]);
                } else {
                    setComments(commentsList.data);
                }
                setLinks(commentsList.links);
                if (commentsList.links) {
                    setMaxPage(parseInt(commentsList.links.last.page));
                } else {
                    setMaxPage(0);
                }
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getComments();
    }, [page, pageSize, props.commentsUrl, update, setErrorStatusCode, props.type]);

    return (<div className="pt-1">
        {(links && comments) &&
            <List>
                {comments.map((comment) => {
                    return <CommentComponent comment={comment}
                                             key={comment.id}
                                             setCommentsUpdate={props.setCommentsUpdate} type={props.type}/>;
                })}
            </List>}
        <div className="flex justify-center">
            {maxPage === 0 ? <NoResults title={t('comments_no_comments')}/> : <>{page !== maxPage ? (
                <button
                    className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline outline-1"
                    onClick={() => setPage(page + 1)}>{t('comments_load_more')}
                </button>) : (<div className="text-base tracking-tight pl-1 text-gray-400">
                {t('comments_no_more')}
            </div>)}</>}
        </div>
    </div>);
}
export default CommentList;