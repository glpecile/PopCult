import {useState} from "react";
import {Link} from "react-router-dom";
import {Trans, useTranslation} from "react-i18next";
import {motion} from "framer-motion";
import FadeIn from "../animation/FadeIn";

const SettingsUserProfile = () => {
    const {t} = useTranslation();

    const user = JSON.parse(localStorage.getItem("user"));
    const currentUsername = user.username;
    const currentEmail = user.email;
    const [currentName, setName] = useState(user.name);
    const [currentImage, setUserImage] = useState(user.image);
    const [imageError, setImageError] = useState(false);
    const [nameError, setNameError] = useState(false);
    const [changePasswordActive, setChangePassword] = useState(false);
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [enteredRepeatedPassword, setEnteredRepeatedPassword] = useState('');
    const [enteredRepeatedPasswordError, setRepeatedPasswordError] = useState(false);

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

    const PasswordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
        event.target.validity.valid ? setPasswordError(false) : setPasswordError(true);
        if (enteredRepeatedPassword.localeCompare('') !== 0) {
            (event.target.value.localeCompare(enteredRepeatedPassword) === 0) ? setRepeatedPasswordError(false) : setRepeatedPasswordError(true);
        }
    };
    
    const RepeatedPasswordChangeHandler = (event) => {
        setEnteredRepeatedPassword(event.target.value);
        (enteredPassword.localeCompare(event.target.value) === 0) ? setRepeatedPasswordError(false) : setRepeatedPasswordError(true);
    };
    const submitHandler = (event) => {
        event.preventDefault();
        if(changePasswordActive){
            if (!(nameError || enteredPasswordError || enteredRepeatedPasswordError)) {
                //    aca se estaria cambiando la contrasenia
            }
        }else if (!(nameError)) {
            const userData = {
                name: currentName, username: currentUsername, image: currentImage
            };
            user.onSaveUserData(userData);
        }
    };
    

    return (<form className="g-3 p-4 my-8 bg-white shadow-lg rounded-lg" onSubmit={submitHandler} noValidate={true}>
        <div className="flex flex-col justify-center items-center">
            <h2 className="text-3xl mb-3">
                <Trans i18nKey="profile_settings_title">
                    {{username: currentUsername}}
                </Trans>
            </h2>
            {/*Profile Pic Row*/}
            <div className="relative inline-block">
                <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                     src={currentImage}/>

            </div>
            <input
                className="text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-violet-50 file:text-violet-700 hover:file:bg-violet-100"
                type='file' onChange={imageChangeHandler} accept="image/gif, image/jpeg, image/png, image/svg"/>


            <FadeIn isActive={imageError}>
                <motion.div key="modal" className="py-1 px-2.5 collapse show" id="alert">
                    <motion.div key="icon"
                                className="alert bg-red-200/95 text-gray-500 d-flex align-items-center shadow-md"
                                role="alert">
                            <span className="absolute top-0 bottom-0 right-0 px-4 py-3">
                                <button onClick={() => setImageError(false)}><i
                                    className="fas fa-times hover:text-gray-800"/></button>
                            </span>
                        <small key="text" id="imageErrorTextBlock"
                               className="form-text text-muted whitespace-pre-wrap">
                            {t('settings_image_error')}
                        </small>
                    </motion.div>
                </motion.div>
            </FadeIn>

            {/*name input*/}
            <div className="py-1 text-semibold w-full">
                <label className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                    {t('register_name')}
                </label>
                <input
                    className={"rounded active:none w-full " + (nameError ? " border-2 border-rose-500" : "")}
                    type='text' value={currentName}
                    onChange={nameChangeHandler} minLength={3} maxLength={100} pattern="[a-zA-Z0-9\s]+"/>
                {nameError &&
                    <p className="text-red-500 text-xs italic">
                        {t('name_error')}
                    </p>
                }
            </div>
            {/* username input*/}
            <div className="py-1 text-semibold w-full">
                <label className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                    {t('register_username')}
                </label>
                <input className="rounded w-full bg-gray-100" type='text' value={currentUsername} disabled={true}/>
            </div>
            {/* email input*/}
            <div className="py-1 text-semibold w-full">
                <label className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                    {t('register_email')}
                </label>
                <input className="rounded w-full bg-gray-100" type='text' value={currentEmail} disabled={true}/>
            </div>

            {/*change password*/}
            {changePasswordActive && <>
                <div className="flex flex-col justify-center items-center mt-5">
                    <h2 className="text-2xl mb-1">
                        {t('profile_settings_changePassword')}
                    </h2>
                </div>
                <div className="py-1 text-semibold w-full">
                    <label
                        className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                        {t('register_password')}
                    </label>
                    <input type="password"
                           className={"w-full rounded active:none " + (enteredPasswordError ? "border-2 border-rose-500" : "")}
                           minLength={8} maxLength={100}
                           defaultValue={enteredPassword} onChange={PasswordChangeHandler}/>
                    {enteredPasswordError &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_password_hint')}
                        </p>}
                </div>

                <div className="py-1 text-semibold w-full">
                    <label className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-purple-400">
                        {t('register_password_repeat')}
                    </label>
                    <input type="password"
                           className={"w-full rounded active:none " + (enteredRepeatedPasswordError ? "border-2 border-rose-500" : "")}
                           minLength={8} maxLength={100} onChange={RepeatedPasswordChangeHandler}/>
                    {enteredRepeatedPasswordError &&
                        <p className="text-red-500 text-xs italic my-1.5">
                            {t('register_password_match_error')}
                        </p>}
                </div>
            </>}
        </div>

        {/*Buttons */}
        <div className="flex justify-between pt-3">
            <div className="flex space-x-3">
                {/*change password*/}
                <button type="button"
                        className="btn rounded my-2 bg-gray-300 group hover:bg-purple-400 hover:text-white text-gray-700"
                        onClick={() => setChangePassword(!changePasswordActive)}>
                    <i className="fas fa-unlock-alt group-hover:text-white mr-2"/>
                    {changePasswordActive ? t('profile_settings_cancelChangePassword') : t('profile_settings_changePassword')}
                </button>
                {/*delete user*/}
                <button type="button" data-bs-toggle="modal" data-bs-target="#deleteUserModal"
                        className="btn my-2 bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                    <i className="fas fa-user-alt-slash group-hover:text-white mr-2"/>
                    {t('profile_settings_deleteUser')}
                </button>
            </div>
            <div className="flex space-x-3">
                <Link to={'/user/' + currentUsername} className='mr-3'>
                    <button
                        className="btn bg-gray-300 group hover:bg-yellow-400 text-gray-700 font-semibold hover:text-white my-2">
                        <i className="fa fa-trash group-hover:text-white mr-2"/>
                        {t('discard_changes')}
                    </button>
                </Link>
                <button type="submit"
                        className={(nameError ? "disabled " : "") + "btn bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white my-2"}>
                    <i className="fas fa-check group-hover:text-white mr-2"/>
                    {t('save_changes')}
                </button>
            </div>
        </div>
    </form>);
}


export default SettingsUserProfile;