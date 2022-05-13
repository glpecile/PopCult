import {useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import listService from "../../../services/ListService";
import {useTranslation} from "react-i18next";
import NoButtonDialog from "../../modal/NoButtonDialog";
import ForksCard from "./ForksCard";

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
                console.log(data);
                setForks(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getForks();
    }, [props.forksUrl, setErrorStatusCode, page]);

    return <>{forks && <> {t('lists_forkedAmount', {times: props.forks})}{props.forks > 0 &&
        <NoButtonDialog
            buttonClassName="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded h-min my-0 py-0 mx-1 px-1 py-1"
            buttonText={t('see_all')}
            title={t('lists_forks')}
            body={forks.data.map((fork) => {
                return <ForksCard key={fork.id} fork={fork}/>
            })}
            isOpened={false}/>}</>}</>;
}
export default ListForks;