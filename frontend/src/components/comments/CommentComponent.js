import {useEffect, useState} from "react";
import UserService from "../../services/UserService";
import {Link} from "react-router-dom";
import {ListItem} from "@mui/material";

const CommentComponent = (props) => {
    const [user, setUser] = useState(undefined);
    const [comment, setComment] = useState(undefined);

    useEffect(() => {
        setComment(props.comment);
    }, [props.comment]);

    useEffect(() => {
        if (comment) {
            async function getUserByUsername() {
                const data = await UserService.getUser(comment.userUrl);
                setUser(data);
            }

            getUserByUsername();
        }
    }, [comment]);

    return (
        <>{(comment && user) &&
            <ListItem className="p-1 my-2 ring-2 ring-gray-200 bg-white rounded-lg flex flex-wrap flex-col">
                <div className="grid grid-cols-12 gap-2">
                    <div><img className="inline-block object-cover rounded-full" alt="profile_image"
                              src={user.imageUrl}/>
                    </div>
                    <div className="col-span-10 flex flex-row items-center text-lg">
                        <Link className="text-decoration-none text-violet-500 hover:text-violet-900"
                              to={'/user/' + user.username}>{user.username}</Link>
                        <div className="text-base tracking-tight pl-1 text-gray-400">
                            &#8226;
                        </div>
                        <div className="text-base tracking-tight pl-1 text-gray-400">
                            {(comment.creationDate).slice(0, 10)}
                        </div>
                    </div>
                    <div>x</div>
                    <div/>
                    <div className="col-span-11 flex items-center lg:pb-2">
                        <div className="m-0 max-w-full break-words"> {comment.commentBody} </div>
                    </div>

                </div>
            </ListItem>}</>
    );
}
export default CommentComponent;