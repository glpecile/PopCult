import MediaCard from "./MediaCard";

function MediaSlider(props) {
    let i = 0;

    const createMedia = (content) => {
        i += 1;
        const id = i.toString();
        return (<div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                     data-slider-target="image"
                     id={id} key={id}><MediaCard key={content.id} id={content.id}
                                                 image={content.image} title={content.title}
                                                 releaseDate={content.releaseDate}/></div>);
    }

    return (
        <div className="flex flex-col" data-controller="slider">
            <div className="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                {props.media.map(content => createMedia(content))}
            </div>
            {/*<div className="flex mx-auto my-2">*/}
            {/*    <ul className="flex justify-center">*/}
            {/*        <li className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator"*/}
            {/*            data-image-id="1" key="1"*/}
            {/*            data-action="click->slider#scrollTo"/>*/}
            {/*        <li className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator"*/}
            {/*            data-image-id="2" key="2"*/}
            {/*            data-action="click->slider#scrollTo"/>*/}
            {/*        <li className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator"*/}
            {/*            data-image-id="3" key="3"*/}
            {/*            data-action="click->slider#scrollTo"/>*/}
            {/*        <li className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator"*/}
            {/*            data-image-id="4" key="4"*/}
            {/*            data-action="click->slider#scrollTo"/>*/}
            {/*        <li className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator"*/}
            {/*            data-image-id="5" key="5"*/}
            {/*            data-action="click->slider#scrollTo"/>*/}
            {/*        <li className="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator"*/}
            {/*            data-image-id="6" key="6"*/}
            {/*            data-action="click->slider#scrollTo"/>*/}
            {/*    </ul>*/}
            {/*</div>*/}
        </div>);
}

export default MediaSlider;