import {useState} from "react";
import CommentService from "../../services/CommentService";

const NewComment = (props) => {
    const [comment, setComment] = useState("");

    const insertComment = (event) => {
        const aux = event.target.value;
        if (!(aux.includes('<') || aux.includes('>'))) setComment(aux);
    }
    const submitHandler = (event) => {
        event.preventDefault();

        async function submitComment() {
            await CommentService.createListComment({url: props.commentsUrl, data: comment});
            setComment("");
        }
        submitComment();
    }
    return (<form onSubmit={submitHandler}>
        <textarea
            placeholder="Say something..."
            className="rounded w-full bg-gray-50 mt-2"
            value={comment} onChange={insertComment}/>
        <div className="flex justify-end">
            <button type="submit"
                className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline outline-1"
                > Comment
            </button>
        </div>
    </form>);
}
export default NewComment;