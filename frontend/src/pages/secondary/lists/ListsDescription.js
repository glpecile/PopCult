import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import Loader from "../errors/Loader";

function ListsDescription() {
    const id = window.location.pathname.split('/')[2];
    const [list, setList] = useState(undefined);

    useEffect(() => {
        async function getList(id) {
            const data = await ListService.getListById(id);
            setList(data);
        }

        getList(id);
    }, [id]);

    return (<>
        {list ? (<>
            <div className="flex flex-wrap pt-2">
                <h2 className="display-5 fw-bolder">
                    {list.name}
                </h2>
                {/*    report and favorite buttons */}
            </div>
            {/*    list author and forking info*/}
            {/*    forked from*/}
            {/*amount of forks*/}
            <p className="lead text-justify max-w-full break-words pb-2">
                {list.description}
            </p>
            {/*    collaborators */}
            {/*    share and edit */}
            {/*<MediaInList media={list.media}/>*/}
            {/*    Comment section*/}</>) : <Loader/>}
    </>);
}

export default ListsDescription;