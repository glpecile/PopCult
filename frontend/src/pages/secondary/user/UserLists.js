import ListsCard from "../../../components/lists/ListsCard";
import {Helmet} from "react-helmet-async";
import {useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import listService from "../../../services/ListService";
import PaginationComponent from "../../../components/PaginationComponent";
import Spinner from "../../../components/animation/Spinner";

const UserLists = () => {
    let {username} = useParams();
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [userLists, setUserLists] = useState(undefined);
    const [page, setPage] = useState(1);
    const pageSize = 8;
    const {setErrorStatusCode} = useErrorStatus();


    const createNewList = () => {
        navigate('/lists/new');
    }

    useEffect(() => {
        async function getUserLists() {
            try {
                const data = await listService.getUserEditableListsByUsername({username, page, pageSize});
                setUserLists(data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getUserLists();
    }, [username, page, pageSize, setErrorStatusCode])

    return (<>
            <Helmet>
                <title>{t('user_lists_header', {username: username})} &#8226; PopCult</title>
            </Helmet>
            {userLists && <div className="row">
                <div className="flex flex-wrap justify-between p-2.5 pb-0">
                    <h2 className="text-3xl fw-bolder py-2">
                        {t('user_lists_title')}
                    </h2>
                    <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                            onClick={createNewList}>
                        {t('lists_newList')}
                    </button>
                    <div className="row py-2">
                        {(userLists) ? (
                            (userLists.data && userLists.data.length > 0) ?
                                (<>{userLists.data.map(content => {
                                    return <div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                                                data-slider-target="image"
                                                key={content.id}><ListsCard id={content.id} key={content.id}
                                                                            mediaUrl={content.mediaUrl}
                                                                            listTitle={content.name}/></div>;
                                })}
                                    <div className="flex justify-center">
                                        {(userLists.data.length > 0 && userLists.links.last.page > 1) &&
                                            <PaginationComponent page={page}
                                                                 lastPage={userLists.links.last.page}
                                                                 setPage={setPage}/>
                                        }
                                    </div>
                                </>)
                                : (<h3 className="text-center text-gray-400">
                                    {t('user_lists_empty')}
                                </h3>)) : (<Spinner/>)
                        }
                    </div>
                </div>
            </div>}
        </>
    );
}
export default UserLists;