import MediaCard from "../media/MediaCard";

function MediaInList(props) {
    const showMedia = (content) => {
        return (
            <div className="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2" key={content.id}>
                <MediaCard key={content.id}
                           id={content.id}
                           image={content.imageUrl}
                           title={content.title}
                           releaseDate={content.releaseDate}
                           type={content.type.toLowerCase()}
                />
            </div>);
    }
    return (<div className="row pb-4">
        {props.media.map(content => showMedia(content))}
    </div>);
}

export default MediaInList;