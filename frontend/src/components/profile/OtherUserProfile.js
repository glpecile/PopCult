function OtherUserProfile(user) {
    return (<>
        <div className="relative inline-block">
            <img className="inline-block object-cover rounded-full h-40 w-40" alt="profile_image"
                 src={user.image}/>
        </div>
        <h2 className="text-3xl font-bold">
            {user.name}
        </h2>
        <h4>
            Or as we like to call you: {user.username}
        </h4>
    </>);
}

export default OtherUserProfile;