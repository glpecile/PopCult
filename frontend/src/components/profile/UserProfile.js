import {Link} from "react-router-dom";

const UserProfile = (user) => {
    const isCurrentUser = true;
    return (<div className="flex flex-col gap-3 justify-center items-center">
        <div className="relative inline-block">
            <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                 src={user.image}/>
        </div>
        <div className="flex justify-center items-center space-x-3">
            <h2 className="text-3xl font-bold">
                {user.name}
            </h2>
            {isCurrentUser && (<Link className="object-center inline-block" to='/settings'>
                <i className="fas fa-user-edit text-purple-500 hover:text-purple-900"/>
            </Link>)}
        </div>
        <h4>
            Or as we like to call you: {user.username}
        </h4>
    </div>);
}

export default UserProfile;