import {useEffect, useState} from "react";
import CommentService from "../../services/CommentService";
import CommentComponent from "./CommentComponent";
import {List} from "@mui/material";
import {useTranslation} from "react-i18next";

const CommentList = (props) => {
    const {t} = useTranslation();

    const pageSize = 2;
    const [page, setPage] = useState(1);
    const [maxPage, setMaxPage] = useState(1);
    const [comments, setComments] = useState([])
    const [links, setLinks] = useState(undefined);


    useEffect(() => {
        async function getComments() {
            const commentsList = await CommentService.getListComments({
                url: props.commentsUrl,
                page: page,
                pageSize: pageSize
            });
            setComments(prevState => [...prevState, ...commentsList.data]);
            setLinks(commentsList.links);
            if (commentsList.links) {
                setMaxPage(parseInt(commentsList.links.last.page));
            }else{
                setMaxPage(0);
            }
        }

        getComments();
    }, [page, pageSize, props.commentsUrl]);

    return (<div className="pt-1">
        {(links) &&
            <List>
                {comments.map((comment) => {
                    return <CommentComponent comment={comment}
                                             key={comment.id}/>;
                })}
            </List>}
        <div className="flex justify-center">
            {maxPage === 0? <div className="text-base tracking-tight pl-1 text-gray-400">{t('comments_no_comments')}</div>: <>{page !== maxPage ? (<button
                className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline outline-1"
                onClick={() => setPage(page + 1)}>{t('comments_load_more')}
            </button>) : (<div className="text-base tracking-tight pl-1 text-gray-400">
                {t('comments_no_more')}
            </div>)}</>}
        </div>
    </div>);
}
export default CommentList;