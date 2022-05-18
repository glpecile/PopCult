import MediaCard from "../media/MediaCard";
import ResponsiveMediaGrid from "../ResponsiveMediaGrid";

function MediaInList(props) {
    const showMedia = (content) => {
        return (
            <div className="p-0 m-0" key={content.id}>
                <MediaCard key={content.id}
                           id={content.id}
                           image={content.imageUrl}
                           title={content.title}
                           releaseDate={content.releaseDate.slice(0, 4)}
                           type={content.type.toLowerCase()}
                />
            </div>);
    }
    return (<ResponsiveMediaGrid>
        {props.media.map(content => showMedia(content))}
    </ResponsiveMediaGrid>);
}

export default MediaInList;