import {useEffect, useState} from "react";
import CommentService from "../../services/CommentService";
import CommentComponent from "./CommentComponent";
import {TransitionGroup} from 'react-transition-group';
import Collapse from "@mui/material/Collapse";
import {List} from "@mui/material";

const CommentList = (props) => {
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
            setMaxPage(parseInt(commentsList.links.last.page) || 1);
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
            {page !== maxPage ? (<button
                className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline outline-1"
                onClick={() => setPage(page + 1)}>See more comments
            </button>) : (<div className="text-base tracking-tight pl-1 text-gray-400">
                It seems there are no more comments to load! :(
            </div>)}
        </div>
    </div>);
}
export default CommentList;