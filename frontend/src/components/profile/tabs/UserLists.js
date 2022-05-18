import ListsCard from "../../lists/ListsCard";
import PaginationComponent from "../../PaginationComponent";
import {useTranslation} from "react-i18next";
import Spinner from "../../animation/Spinner";
import ResponsiveMediaGrid from "../../ResponsiveMediaGrid";
import NoResults from "../../search/NoResults";

const UserLists = (props) => {
    const {t} = useTranslation();


    return <>{
        props.lists ? (props.lists.data && props.lists.data.length > 0) ?
            (<>
                <ResponsiveMediaGrid>
                    {props.lists.data.map((list) => {
                        return (<div key={list.id} className="m-0 p-0">
                            <ListsCard id={list.id} key={list.id}
                                       mediaUrl={list.mediaUrl}
                                       listTitle={list.name}/>
                        </div>);
                    })}
                </ResponsiveMediaGrid>
                <div className="flex justify-center pt-4">
                    {(props.lists.data.length > 0 && props.lists.links.last.page > 1) &&
                        <PaginationComponent page={props.page}
                                             lastPage={props.lists.links.last.page}
                                             setPage={props.setPage}/>
                    }
                </div>
            </>)
            :
            (<NoResults title={t('profile_tabs_noContent')}/>) : <Spinner/>
    }</>;
}
export default UserLists;