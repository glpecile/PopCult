import ListsCard from "./ListsCard";
import {IconButton} from "@mui/material";
import AliceCarousel from "react-alice-carousel";
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';

const handleDragStart = (e) => e.preventDefault();

const ListsSlider = (props) => {
    const responsive = {
        0: {items: 1},
        568: {items: 2},
        1024: {items: 4},
    };
    let buttonStyle = " absolute top-1/3 absolute top-1/3 h-8 w-8 rounded-full drop-shadow-md cursor-pointer bg-slate-50 hover:bg-slate-200 transition duration-300 ease-in-out transform active:scale-90";

    const createItems = (content) => {
        return (
            <div className="px-2.5 py-3">
                <ListsCard id={content.id} key={content.id}
                           mediaUrl={content.mediaUrl}
                           listTitle={content.name}/>
            </div>
        );
    }

    const renderDotsItem = ({isActive}) => {
        return isActive ?
            <div className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-purple-500 hover:bg-purple-900">
            </div> : <div className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900">
            </div>;
    };

    const renderNext = () => {
        return <IconButton type="button" className={"-right-4" + buttonStyle}>
            <NavigateNextIcon/>
        </IconButton>;
    }

    const renderPrev = () => {
        return <IconButton type="button" className={"-left-2" + buttonStyle}>
            <NavigateBeforeIcon/>
        </IconButton>;
    }

    return (
        <>
            <AliceCarousel mouseTracking
                           animationDuration="150"
                           infinite
                           touchTracking
                           touchMoveDefaultEvents
                           controlsStrategy="responsive"
                           renderNextButton={renderNext}
                           renderPrevButton={renderPrev}
                           renderDotsItem={renderDotsItem}
                           responsive={responsive}
                           paddingRight={50}
                           keyboardNavigation
                           items={props.lists.map(content => createItems(content))}
            />

        </>
    );
}

export default ListsSlider
