import CommentList from "./CommentList";
import NewComment from "./NewComment";
import {Divider} from "@mui/material";

const CommentSection = (props) => {

    return (
        <div className="py-2">
            <Divider className="text-violet-500"/>
            <div className="text-xl text-justify max-w-full break-words pt-2">Comments</div>
            <NewComment commentsUrl={props.commentsUrl}/>
            <CommentList/>
        </div>);
}
export default CommentSection;