import {useState} from "react";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

const SettingsUserProfile = (user) => {
    const {t} = useTranslation();

    const [currentUsername, setUsername] = useState(user.username);
    const [currentName, setName] = useState(user.name);
    const [currentImage, setUserImage] = useState(user.image);
    const [imageError, setImageError] = useState(false);
    const [nameError, setNameError] = useState(false);
    const [usernameError, setUsernameError] = useState(false);

    const usernameChangeHandler = (event) => {
        event.target.validity.valid ? setUsernameError(false) : setUsernameError(true);
        setUsername(event.target.value);
    };
    const nameChangeHandler = (event) => {
        event.target.validity.valid ? setNameError(false) : setNameError(true);
        setName(event.target.value);
    };
    const imageChangeHandler = (event) => {
        event.target.validity.valid ? setImageError(false) : setImageError(true);
        if (event.target.files[0].size > 1048576 * 2) {
            setImageError(true); //2MB
            setTimeout(() => {
                setImageError(false);
            }, 5000);
        } else if (event.target.validity.valid && event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            setUserImage(URL.createObjectURL(img));
        }
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if (!(nameError || usernameError)) {
            const userData = {
                name: currentName, username: currentUsername, image: currentImage
            };
            user.onSaveUserData(userData);
        }
    };

    return (<form onSubmit={submitHandler} noValidate={true}>
        <div className=" relative flex flex-col gap-3 justify-center items-center">
            {/*Profile Pic Row*/}
            <div className="relative inline-block">
                <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                     src={currentImage}/>
            </div>
            <input type='file' onChange={imageChangeHandler} accept="image/gif, image/jpeg, image/png, image/svg"/>


            {/*    username and edit */}
            <div className="py-1 px-2.5">
                <input
                    className={"rounded active:none text-3xl font-bold " + (nameError ? " border-2 border-rose-500" : "")}
                    type='text' value={currentName}
                    onChange={nameChangeHandler} minLength={3} maxLength={100} pattern="[a-zA-Z0-9\s]+"/>
                {nameError &&
                    <p className="text-red-500 text-xs italic">
                        {t('name_error')}
                    </p>
                }
            </div>
            <div className="py-1 px-2.5">
                <div className="flex justify-center items-center space-x-3">
                    <h4 className="text-base">{t('settings_description')}</h4>
                    <input
                        className={"rounded active:none text-xl font-bold " + (usernameError ? " border-2 border-rose-500" : "")}
                        type='text' value={currentUsername}
                        pattern="[a-zA-Z0-9]+" minLength={1} maxLength={100}
                        onChange={usernameChangeHandler}/>
                </div>
                {usernameError &&
                    <p className="text-red-500 text-xs italic text-center">
                        {t('username_error')}
                    </p>}
            </div>
            {imageError && <div className="absolute bottom-0 collapse show z-50 fixed" id="alert">
                <div className="alert bg-rose-500/90 text-gray-700 d-flex align-items-center shadow-lg"
                     role="alert">
                            <span className="absolute top-0 bottom-0 right-0 px-4 py-3">
                                <button onClick={() => setImageError(false)}><i className="fas fa-times"/></button>
                            </span>
                    <small className="text-gray-700 flex flex-wrap">
                        {t('settings_image_error')}
                    </small>
                </div>
            </div>}
            <div className="m-3">
                <Link to='/user/a' className='mr-3'>
                    <button
                        className="btn btn-danger bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                        <i className="fa fa-trash group-hover:text-white mr-2"/>
                        {t('discard_changes')}
                    </button>
                </Link>
                <button type="submit"
                        className={((nameError || usernameError) ? "disabled " : "") + "btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white"}>
                    <i className="fas fa-check group-hover:text-white mr-2"/>
                    {t('save_changes')}
                </button>
            </div>
        </div>
    </form>);
}

export default SettingsUserProfile;