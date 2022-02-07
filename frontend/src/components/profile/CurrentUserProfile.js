import {useState} from "react";
import {Link} from "react-router-dom";

function CurrentUserProfile(user) {
    const [userImage, setUserImage] = useState('');
    const uploadImage = () => {
        console.log(userImage);
        setUserImage(user.image);
        //upload modal
        return (<input type="file"/>);
    }
    return (<>
            {/*Profile Pic Row*/}
            <div className="relative inline-block">
                <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                     src={user.image}/>
                {/*Edit Button*/}
                <button type="button"
                        className="absolute top-0 right-0 inline-block btn btn-white w-10 h-10 p-0 text-gray-400 hover:text-gray-900 btn-rounded rounded-full"
                        onClick={uploadImage}>
                    <i className="fas fa-pencil-alt text-gray-500"/>
                </button>
                {/*TODO Role Badge*/}
            </div>
            {/*    username and edit */}
            <div className="flex justify-center items-center space-x-3">
                <h2 className="text-3xl font-bold">{user.name}</h2>
                <Link className="object-center inline-block" to='/settings'>
                    <i className="fas fa-user-edit text-purple-500 hover:text-purple-900"/>
                </Link>
            </div>
            <h4>Or as we like to call you: {user.username}</h4>

        </>
    );
}

export default CurrentUserProfile;