import ProfileInformation from "../../../components/profile/ProfileInformation";
const DUMMY_USER = {
    userid: 1,
    image: "https://pbs.twimg.com/media/Ec5mKYbUMAAKx7A.jpg",
    name: "Gengar Gengar",
    username: "Genga",
};

function Profile () {
    return (<ProfileInformation id={DUMMY_USER.userid} name={DUMMY_USER.name} username={DUMMY_USER.username} image={DUMMY_USER.image}/>);
}
export default Profile;