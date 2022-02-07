import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";

const DUMMY_USER = {
    userid: 1,
    image: "https://pbs.twimg.com/media/Ec5mKYbUMAAKx7A.jpg",
    name: "Gengar Gengar",
    username: "Genga",
};

const Settings = () => {
    const updateUserData = (props) => {
        console.log(props);
    }
    return (<SettingsUserProfile name={DUMMY_USER.name} username={DUMMY_USER.username} image={DUMMY_USER.image} onSaveUserData={updateUserData}/>);
}
export default Settings;