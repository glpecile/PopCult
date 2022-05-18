import ListsCard from "../../../components/lists/ListsCard";
import {Helmet} from "react-helmet-async";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useContext, useEffect, useState} from "react";
import useErrorStatus from "../../../hooks/useErrorStatus";
import listService from "../../../services/ListService";
import PaginationComponent from "../../../components/PaginationComponent";
import Spinner from "../../../components/animation/Spinner";
import UserContext from "../../../store/UserContext";
import Loader from "../errors/Loader";
import ResponsiveMediaGrid from "../../../components/ResponsiveMediaGrid";

const UserLists = () => {
    const user = useContext(UserContext).user;
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [userLists, setUserLists] = useState(undefined);
    const [page, setPage] = useState(1);
    const pageSize = 12;
    const {setErrorStatusCode} = useErrorStatus();


    const createNewList = () => {
        navigate('/lists/new');
    }

    useEffect(() => {
        async function getUserLists() {
            if (user !== undefined) {
                try {
                    const data = await listService.getMediaLists({url: user.editableListsUrl, page, pageSize});
                    setUserLists(data);
                } catch (error) {
                    setErrorStatusCode(error.response.status);
                }
            }
        }

        getUserLists();
    }, [user, page, pageSize, setErrorStatusCode])

    return (<> {
        user ? <>
            <Helmet>
                <title>{t('user_lists_header', {username: user.username})} &#8226; PopCult</title>
            </Helmet>
            {userLists && <div className="row">
                <div className="flex flex-wrap justify-between p-3.5 mb-2">
                    <h2 className="text-4xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide">
                        {t('user_lists_title')}
                    </h2>
                    <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                            onClick={createNewList}>
                        {t('lists_newList')}
                    </button>
                </div>
                {(userLists) ? (
                    (userLists.data && userLists.data.length > 0) ?
                        (<div>
                            <ResponsiveMediaGrid>
                                {userLists.data.map(content => {
                                    return <div className="m-0 p-0"
                                                data-slider-target="image"
                                                key={content.id}>
                                        <ListsCard id={content.id} key={content.id}
                                                   mediaUrl={content.mediaUrl}
                                                   listTitle={content.name}/>
                                    </div>;
                                })}
                            </ResponsiveMediaGrid>
                            <div className="flex justify-center pt-4">
                                {(userLists.data.length > 0 && userLists.links.last.page > 1) &&
                                    <PaginationComponent page={page}
                                                         lastPage={userLists.links.last.page}
                                                         setPage={setPage}/>
                                }
                            </div>
                        </div>)
                        : (<h3 className="text-center text-gray-400">
                            {t('user_lists_empty')}
                        </h3>)) : (<Spinner/>)
                }
            </div>}
        </> : <Loader/>
    }
    </>);
}
export default UserLists;