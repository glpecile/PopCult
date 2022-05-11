import {useContext} from "react";
import ErrorStatusContext from "../store/ErrorStatusContext";

const useErrorStatus = () => useContext(ErrorStatusContext);

export default useErrorStatus;