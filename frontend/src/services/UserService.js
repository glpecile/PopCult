import axios from "axios";

const UserService = async (props) => {
    const res = await axios('http://localhost:8080/webapp_war/users/'+props).catch(
        e => {
            console.log(e)
        //    aca va el manejo de errores
        }
    );
    return await res.data;
}

export default UserService;