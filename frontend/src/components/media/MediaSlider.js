import MediaCard from "./MediaCard";
import AliceCarousel from "react-alice-carousel";

const handleDragStart = (e) => e.preventDefault();

const MediaSlider = (props) => {
    const responsive = {
        0: {items: 1},
        568: {items: 2},
        1024: {items: 4},
    };


    const createItems = (content) => {
        return (
            <div className="px-2 py-3">
                <MediaCard
                    key={content.id} id={content.id}
                    image={content.image} title={content.title}
                    releaseDate={content.releaseDate}
                    onDragStart={handleDragStart}
                    role="presentation"
                />
            </div>
        );
    }

    return (
        <AliceCarousel mouseTracking
                       touchTracking
                       touchMoveDefaultEvents
                       controlsStrategy="responsive"
                       disableButtonsControls
                       responsive={responsive}
                       keyboardNavigation={true}
                       items={props.media.map(content => createItems(content))}/>
    );
}

export default MediaSlider;