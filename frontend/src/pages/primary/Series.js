import MediaSlider from "../../components/media/MediaSlider";

const DUMMY_DATA = [
    {
        id: 1,
        image:"https://w0.peakpx.com/wallpaper/474/404/HD-wallpaper-levi-anime-anime-aot-attackontitan-attackontitan-leviackerman-leviackermann-levi-shingekinokyojin.jpg",
        title:"Attack on Titan",
        releaseDate:"Still on air.",
    },
    {
        id: 2,
        image:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
        title:"Yuri!!! On ICE",
        releaseDate:"2016",
    }, {
        id: 3,
        image:"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRrq6cRK12zrAuOlcUjC7auIPLMdvUJSlS0IzoYkaGF5mOYFim1",
        title:"The Witcher",
        releaseDate:"2019",
    }, {
        id: 4,
        image:"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQMpWgFcYiV0MThcMMAVihr_5Zx2twoZ2Q_kVZOsVZ9nH1hmJZj",
        title:"Friends",
        releaseDate:"1994",
    }, {
        id: 5,
        image:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0DPn96_u8S7YgrrsW7BJnIX-9z_PPALU2_0EtBDJ5c3y2u4oB",
        title:"How I Met Your Mother",
        releaseDate:"2005",
    }, {
        id: 6,
        image:"https://m.media-amazon.com/images/M/MV5BNzVkYWY4NzYtMWFlZi00YzkwLThhZDItZjcxYTU4ZTMzMDZmXkEyXkFqcGdeQXVyODUxOTU0OTg@._V1_.jpg",
        title:"Brooklyn Nine-Nine",
        releaseDate:"2013",
    },
];

function Series() {
    return (
        <section>
            <h4 className="font-bold text-2xl pt-2">
                Series Page</h4>
            <MediaSlider media={DUMMY_DATA}/>
        </section>
    );
}

export default Series;