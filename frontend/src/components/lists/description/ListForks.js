import {useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import listService from "../../../services/ListService";
import {useTranslation} from "react-i18next";

const ListForks = (props) => {
    const [forks, setForks] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();
    const [page, setPage] = useState(1);
    const {t} = useTranslation();

    console.log(forks)

    useEffect(() => {
        async function getForks() {
            try {
                const data = await listService.getListForks({url: props.forksUrl, page: page, pageSize: 4});
                setForks(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getForks();
    }, [props.forksUrl, setErrorStatusCode, page]);

    return <>{forks && <>{t('lists_forkedAmount')}
        <div>
        </div>
    </>}</>
}
export default ListForks;