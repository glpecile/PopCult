import {useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import listService from "../../../services/ListService";

const ListForks = (props) => {
    const [forks, setForks] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getForks() {
            try {
                const data = await listService.getList(props.forksUrl);
                console.log(data);
                setForks(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getForks();
    }, [props.forksUrl, setErrorStatusCode])
    return <>{forks && <div>hola</div>}</>
}
export default ListForks;