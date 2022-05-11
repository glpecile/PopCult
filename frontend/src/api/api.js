import axios from "axios";
import Qs from "qs";

export default axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    timeout: 5000,
    paramsSerializer: params => Qs.stringify(params, {arrayFormat: 'repeat'})
});