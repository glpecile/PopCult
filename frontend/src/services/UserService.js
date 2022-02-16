
const UserService = () => {
    return fetch('http://localhost:8080/webapp_war/users/pau').then(data => data.json());
}

export default UserService;