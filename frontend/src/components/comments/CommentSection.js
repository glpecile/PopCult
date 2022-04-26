import CommentList from "./CommentList";
import NewComment from "./NewComment";
import {useState} from "react";

const CommentSection = (props) => {
    return (
        <div className="flex flex-col bg-white shadow-md rounded-lg p-3 pt-0 mt-3">
            <div className="text-xl text-justify max-w-full break-words pt-2">Comments</div>
            <div className="px-2">
                <NewComment commentsUrl={props.commentsUrl}/>
                <CommentList commentsUrl={props.commentsUrl}/>
            </div>
        </div>
    );
}
export default CommentSection;