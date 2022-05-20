import {useEffect, useState} from "react";
import ListService from "../../../services/ListService";
import Loader from "../errors/Loader";
import CommentSection from "../../../components/comments/CommentSection";
import useErrorStatus from "../../../hooks/useErrorStatus";
import ListCollaborators from "../../../components/lists/description/ListCollaborators";
import ListMedia from "../../../components/lists/description/ListMedia";
import {Trans} from "react-i18next";
import ListUpperIcons from "../../../components/lists/description/ListUpperIcons";
import {Link, useParams} from "react-router-dom";
import ListLowerIcons from "../../../components/lists/description/ListLowerIcons";
import ListForks from "../../../components/lists/description/ListForks";
import {CommentSectionType} from "../../../enums/CommentSectionType";
import {Helmet} from "react-helmet-async";

function ListsDescription() {
    let {id} = useParams();
    const [list, setList] = useState(undefined);

    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getList(id) {
            try {
                const data = await ListService.getListById(id);
                setList(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getList(id);
        return () => {

        }
    }, [id, setErrorStatusCode]);


    return (<>
        {list ? (<>
            <Helmet>
                <title>
                    {list.name} • PopCult
                </title>
            </Helmet>
            <div className="flex flex-wrap pt-2">
                <h2 className="text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide">
                    {list.name}
                </h2>
                <ListUpperIcons owner={list.owner} favoriteUrl={list.favoriteUrl} reportsUrl={list.reportsUrl}/>
            </div>
            {/*    list author and forking info*/}
            <div className="flex justify-right">
                <Trans i18nKey="list_by">
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/user/${list.owner}`}>{{username: list.owner}}</Link>
                </Trans>

                {list.forkedFrom && <Trans i18nKey="forked_from">
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/lists/${(list.forkedFromUrl.split('/').pop())}`}>{{list: list.forkedFrom}}</Link>
                </Trans>}
                <ListForks forksUrl={list.forksUrl} forks={list.forks}/>
            </div>
            <p className="lead text-justify max-w-full break-words pb-2">
                {list.description}
            </p>
            {/* collaborators */}
            <ListCollaborators collaboratorsUrl={list.collaboratorsUrl}/>
            {/* share edit and fork */}
            <ListLowerIcons id={id} collaborative={list.collaborative} owner={list.owner} url={list.forksUrl}
                            collaborativeRequestUrl={list.requestsUrl}/>
            <ListMedia mediaUrl={list.mediaUrl}/>
            <CommentSection commentsUrl={list.commentsUrl} type={CommentSectionType.LISTS}/>
        </>) : <Loader/>}
    </>);
}

export default ListsDescription;