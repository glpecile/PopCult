import {useContext, useState} from "react";
import CommentService from "../../services/CommentService";
import {useTranslation} from "react-i18next";
import AuthContext from "../../store/AuthContext";
import {useLocation, useNavigate} from "react-router-dom";
import useErrorStatus from "../../hooks/useErrorStatus";
import {CommentSectionType} from "../../enums/CommentSectionType";

const NewComment = (props) => {
    const {t} = useTranslation();
    const userIsLogged = useContext(AuthContext).isLoggedIn;
    const location = useLocation()
    const navigate = useNavigate();

    const [comment, setComment] = useState("");
    const {setErrorStatusCode} = useErrorStatus();

    const insertComment = (event) => {
        const aux = event.target.value;
        if (!(aux.includes('<') || aux.includes('>'))) setComment(aux);
    }
    const submitHandler = (event) => {
        event.preventDefault();

        async function submitComment() {
            try {
                if (props.type === CommentSectionType.LISTS)
                    await CommentService.createListComment({url: props.commentsUrl, data: comment});
                else if (props.type === CommentSectionType.MEDIA)
                    await CommentService.createMediaComment({url: props.commentsUrl, data: comment});
                setComment("");
                props.setCommentsUpdate(prev => prev + 1);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        if (!userIsLogged) {
            navigate('/login', {
                state: {
                    url: location.pathname
                }
            })
        } else {
            submitComment();
        }
    }

    return (<form onSubmit={submitHandler}>
        <textarea
            placeholder={t('comments_placeholder')}
            className="rounded w-full bg-gray-50 mt-2"
            value={comment} onChange={insertComment}/>
        <div className="flex justify-end">
            <button type="submit" disabled={comment.length === 0 || comment.length > 1000}
                    className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded outline outline-1"
            > {t('comments_submit_comment')}
            </button>
        </div>
    </form>);
}
export default NewComment;