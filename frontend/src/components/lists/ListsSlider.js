import ListsCard from "./ListsCard";
function ListsSlider(props) {
    const createMedia = (content) => {
        return (<div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                     data-slider-target="image"
                     key={content.id} ><ListsCard id={content.id} key={content.id} image1={content.image1} image2={content.image1} image3={content.image1} image4={content.image1} listTitle={content.listTitle}/></div>);
    }

    return (
        <div className="flex flex-col" data-controller="slider">
            <div className="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                {props.media.map(content => createMedia(content))}
            </div>
        </div>
    );
}

export default ListsSlider
