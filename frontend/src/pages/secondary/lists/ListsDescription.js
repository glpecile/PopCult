import MediaInList from "../../../components/lists/MediaInList";

const list = {
    id: 1,
    listTitle: "Yuri's List",
    releaseDate: "2016",
    description: "Good for them! Good for them!",
    media: [{
        id: 1,
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
        title: "Yuri!!! On ICE",
        releaseDate: "2016",
    }, {
        id: 2,
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
        title: "Yuri!!! On ICE",
        releaseDate: "2016",
    }, {
        id: 3,
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
        title: "Yuri!!! On ICE",
        releaseDate: "2016",
    }, {
        id: 4,
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
        title: "Yuri!!! On ICE",
        releaseDate: "2016",
    },],

};

function ListsDescription() {
    const id = window.location.pathname.split('/')[2];
    console.log(id); //list id
    return (<>
        <div className="flex flex-wrap pt-2">
            <h2 className="display-5 fw-bolder">
                {list.listTitle}
            </h2>
            {/*    report and favorite buttons */}
        </div>
        {/*    list author and forking info*/}
        {/*    forked from*/}
        {/*amount of forks*/}
        <p className="lead text-justify max-w-full break-words pb-2">
            {list.description}
        </p>
        {/*    collaborators */}
        {/*    share and edit */}
        <MediaInList media={list.media}/>
        {/*    Comment section*/}
    </>);
}

export default ListsDescription;