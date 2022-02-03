import ListsSlider from "../components/ListsSlider";

const DUMMY_DATA = [{
    id: 1,
    image1: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
    listTitle: "Yuri's List",
    releaseDate: "2016",
}];

function Lists() {
    return (<section>
            <h4 className="font-bold text-2xl pt-2">
                Lists Page</h4>
            <ListsSlider media={DUMMY_DATA}/>
        </section>
    );
}

export default Lists;