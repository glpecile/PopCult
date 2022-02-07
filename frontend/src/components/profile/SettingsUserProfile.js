import {useState} from "react";

const SettingsUserProfile = (user) => {
    const [currentUsername, setUsername] = useState(user.username);
    const [currentName, setName] = useState(user.name);
    const [currentImage, setUserImage] = useState(user.image);

    const usernameChangeHandler = (event) => {
        setUsername(event.target.value);
    };
    const nameChangeHandler = (event) => {
        setName(event.target.value);
    };
    const imageChangeHandler = (event) => {
        if (event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            setUserImage({
                image: URL.createObjectURL(img)
            });
    }
    }

    const submitHandler = (event) => {
        event.preventDefault();
        const userData = {
            name: currentName, username: currentUsername, image: currentImage
        };
        user.onSaveUserData(userData);
    };

    return (<form onSubmit={submitHandler}>
            <div className="flex flex-col gap-3 justify-center items-center">
                {/*Profile Pic Row*/}
                <div className="relative inline-block">
                    <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                         src={currentImage}/>
                </div>
                <input type='file' onChange={imageChangeHandler}/>
                {/*    username and edit */}
                <input className="text-3xl font-bold" type='text' value={currentName} onChange={nameChangeHandler}/>
                <div className="flex justify-center items-center space-x-3">
                    <h4>Or as we like to call you:</h4>
                    <input className="text-xl font-bold" type='text' value={currentUsername}
                           onChange={usernameChangeHandler}/>
                </div>
                <button type="submit">Save Changes</button>
            </div>
        </form>
    );
}

export default SettingsUserProfile;