import {useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import listService from "../../../services/ListService";
import {useTranslation} from "react-i18next";
import CompactListsCard from "../CompactListsCard";
import PaginatedDialog from "../../modal/PaginatedDialog";

const ListForks = (props) => {
    const [forks, setForks] = useState(undefined);
    const {setErrorStatusCode} = useErrorStatus();
    const [page, setPage] = useState(1);
    const {t} = useTranslation();

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

    return <>
        {(forks && forks.data)&&
            <> {t('lists_forkedAmount', {times: props.forks})}{props.forks > 0 &&
                <PaginatedDialog
                    buttonClassName="btn btn-link text-violet-500 group hover:text-violet-900 btn-rounded h-min my-0 py-0 mx-1 px-1 py-1"
                    buttonText={t('see_all')}
                    title={t('lists_forks')}
                    body={forks.data.map((fork) => {
                        return <CompactListsCard className={"pr-3 my-1"} key={fork.id} fork={fork}/>
                    })}
                    isOpened={false}
                    forks={forks}
                    page={page} setPage={setPage}/>
            }
            </>}
    </>;
}
export default ListForks;