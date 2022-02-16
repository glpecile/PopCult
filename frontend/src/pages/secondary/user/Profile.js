import UserProfile from "../../../components/profile/UserProfile";
import UserTabs from "../../../components/profile/UserTabs";



const Profile = (props) => {
    return (
        <>
            <UserProfile id={props.user.username} name={props.user.name} username={props.user.username}
                         image={''}/>
            <UserTabs username={props.user.username} id={props.user.username}/>
        </>);
}

export default Profile;