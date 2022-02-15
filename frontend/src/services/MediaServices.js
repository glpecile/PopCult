import axios from "axios";
import {useEffect, useState} from "react";

const MediaServices = async () => {

    const [ret, setRet] = useState();
    useEffect(() => {
        axios
            .get('http://localhost:8080/webapp_war/users/pau')
            .then((response) => {
                console.log(response);
                setRet(response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
    });

    return ret;
}

export default MediaServices;