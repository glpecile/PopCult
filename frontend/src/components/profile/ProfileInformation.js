import OtherUserProfile from "./OtherUserProfile";
import CurrentUserProfile from "./CurrentUserProfile";

function ProfileInformation(props) {
    let isCurrentUser = true;
    return (
        <div className="flex flex-col gap-3 justify-center items-center">
            {/*    if authenticated */}
            {isCurrentUser && <CurrentUserProfile image={props.image} name={props.name} username={props.username}/>}
            {/*    if not authenticated  */}
            {!isCurrentUser && <OtherUserProfile image={props.image} name={props.name} username={props.username}/>}
        </div>);
}

export default ProfileInformation;