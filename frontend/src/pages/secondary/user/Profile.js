import UserProfile from "../../../components/profile/UserProfile";

const DUMMY_USER = {
    userid: 1,
    image: "https://pbs.twimg.com/media/Ec5mKYbUMAAKx7A.jpg",
    name: "Gengar Gengar",
    username: "Genga",
};

const Profile = () => {
    return (<UserProfile id={DUMMY_USER.userid} name={DUMMY_USER.name} username={DUMMY_USER.username}
                         image={DUMMY_USER.image}/>);
}

export default Profile;