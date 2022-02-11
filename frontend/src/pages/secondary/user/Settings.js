import SettingsUserProfile from "../../../components/profile/SettingsUserProfile";
import {useState} from "react";
import {Navigate} from "react-router-dom";

const DUMMY_USER = {
    userid: 1,
    image: "https://pbs.twimg.com/media/Ec5mKYbUMAAKx7A.jpg",
    name: "Gengar Gengar",
    username: "Genga",
};

const Settings = () => {
    const [successfulUpdate, setSuccessfulUpdate] = useState(false);
    const updateUserData = (props) => {
        setSuccessfulUpdate(true);
    }
    return (<>
        <SettingsUserProfile name={DUMMY_USER.name} username={DUMMY_USER.username} image={DUMMY_USER.image}
                                   onSaveUserData={updateUserData}/>
        {successfulUpdate && <Navigate to='/user/a'/>}
    </>);
}
export default Settings;