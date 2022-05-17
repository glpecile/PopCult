import MediaCard from "../../media/MediaCard";
import PaginationComponent from "../../PaginationComponent";
import {useTranslation} from "react-i18next";
import Spinner from "../../animation/Spinner";
import ResponsiveMediaGrid from "../../ResponsiveMediaGrid";

const UserMedia = (props) => {
    const {t} = useTranslation();


    return <>{
        props.media ? (props.media.data && props.media.data.length > 0) ?
            (<>
                <ResponsiveMediaGrid>
                    {props.media.data.map((content) => {
                        return (
                            <div key={content.id} className="m-0 p-0">
                                <MediaCard key={content.id}
                                           id={content.id}
                                           image={content.imageUrl}
                                           title={content.title}
                                           releaseDate={content.releaseDate.slice(0, 4)}
                                           type={content.type.toLowerCase()}/>
                            </div>
                        );
                    })}
                </ResponsiveMediaGrid>
                <div className="flex justify-center">
                    {(props.media.data.length > 0 && props.media.links.last.page > 1) &&
                        <PaginationComponent page={props.page}
                                             lastPage={props.media.links.last.page}
                                             setPage={props.setPage}/>
                    }
                </div>
            </>)
            :
            (<div className="flex justify-center text-gray-400">{t('profile_tabs_noContent')}</div>) : <Spinner/>
    }</>;
}
export default UserMedia;