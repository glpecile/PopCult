import ListsCard from "../../lists/ListsCard";
import PaginationComponent from "../../PaginationComponent";
import {useTranslation} from "react-i18next";
import Spinner from "../../animation/Spinner";

const UserLists = (props) => {
    const {t} = useTranslation();


    return <>{
        props.lists ? (props.lists.data && props.lists.data.length > 0) ?
            (<>
                <div className="row pb-2">{props.lists.data.map((list) => {
                    return (<div key={list.id} className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                        <ListsCard id={list.id} key={list.id}
                                   mediaUrl={list.mediaUrl}
                                   listTitle={list.name}/>
                    </div>);
                })}</div>
                <div className="flex justify-center">
                    {(props.lists.data.length > 0 && props.lists.links.last.page > 1) &&
                        <PaginationComponent page={props.page}
                                             lastPage={props.lists.links.last.page}
                                             setPage={props.setPage}/>
                    }
                </div>
            </>)
            :
            (<div className="flex justify-center text-gray-400">{t('profile_tabs_noContent')}</div>) : <Spinner/>
    }</>;
}
export default UserLists;