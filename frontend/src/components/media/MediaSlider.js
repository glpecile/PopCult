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
            <div className="px-2.5 py-3">
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

    const renderDotsItem = ({isActive}) => {
        return isActive ?
            <div className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-purple-500 hover:bg-purple-900"> </div> :
            <div className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"> </div>;
    };

    const renderNext = () => {
        return <button className="absolute top-1/3 -right-4 h-8 w-8 rounded-full drop-shadow-md cursor-pointer bg-slate-50 hover:bg-slate-200">
            <i className="fas fa-angle-right"> </i>
        </button>;
    }

    const renderPrev = () => {
        return <button className="absolute top-1/3 -left-2 h-8 w-8 rounded-full drop-shadow-md cursor-pointer bg-slate-50 hover:bg-slate-200">
            <i className="fas fa-angle-left"> </i>
        </button>;
    }

    return (
        <>
            <AliceCarousel mouseTracking
                           touchTracking
                           touchMoveDefaultEvents
                           controlsStrategy="responsive"
                           renderNextButton={renderNext}
                           renderPrevButton={renderPrev}
                           renderDotsItem={renderDotsItem}
                           responsive={responsive}
                           paddingRight={50}
                           keyboardNavigation
                           items={props.media.map(content => createItems(content))}
            />

        </>
    );
}

export default MediaSlider;