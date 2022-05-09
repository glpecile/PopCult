import MediaCard from "../../media/MediaCard";
import PaginationComponent from "../../PaginationComponent";
import {useTranslation} from "react-i18next";
import Spinner from "../../animation/Spinner";

const UserMedia = (props) => {
    const {t} = useTranslation();
    const maxPages = 4;


    return <>{
        props.media ? (props.media.data && props.media.data.length > 0) ?
            (<>
                <div className="row pb-2">{props.media.data.map((content) => {
                    return (<div key={content.id} className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                        <MediaCard key={content.id}
                                   id={content.id}
                                   image={content.imageUrl}
                                   title={content.title}
                                   releaseDate={content.releaseDate.slice(0, 4)}
                                   type={content.type.toLowerCase()}/>
                    </div>);
                })}</div>
                <div className="flex justify-center">
                    {(props.media.data.length > 0 && props.media.links.last.page > 1) &&
                        <PaginationComponent page={props.page}
                                             lastPage={props.media.links.last.page > maxPages ? maxPages : props.media.links.last.page}
                                             setPage={props.setPage}/>
                    }
                </div>
            </>)
            :
            (<div className="flex justify-center text-gray-400">{t('profile_tabs_noContent')}</div>) : <Spinner/>
    }</>;
}
export default UserMedia;