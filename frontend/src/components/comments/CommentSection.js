import CommentList from "./CommentList";
import NewComment from "./NewComment";
import {useTranslation} from "react-i18next";

const CommentSection = (props) => {
    const {t} = useTranslation();

    return (
        <div className="flex flex-col bg-white shadow-md rounded-lg p-3 pt-0 mt-3">
            <div className="text-xl text-justify max-w-full break-words pt-2">{t('comments_title')}</div>
            <div className="px-2">
                <NewComment commentsUrl={props.commentsUrl}/>
                <CommentList commentsUrl={props.commentsUrl}/>
            </div>
        </div>
    );
}
export default CommentSection;