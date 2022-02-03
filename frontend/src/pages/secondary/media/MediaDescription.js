import MediaDetails from "../../../components/media/MediaDetails";

const DUMMY_DETAILS = {
    id: 2,
    image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
    title: "Yuri!!! On ICE",
    releaseDate: "2016",
    country: "Japan",
    description: "Reeling from his crushing defeat at the Grand Prix Finale, Yuuri Katsuki, once Japan's most promising figure skater, returns to his family home to assess his options for the future. At age 23, Yuuri's window for success in skating is closing rapidly, and his love of pork cutlets and aptitude for gaining weight are not helping either.\nHowever, Yuuri finds himself in the spotlight when a video of him performing a routine previously executed by five-time world champion, Victor Nikiforov, suddenly goes viral. In fact, Victor himself abruptly appears at Yuuri's house and offers to be his mentor. As one of his biggest fans, Yuuri eagerly accepts, kicking off his journey to make it back onto the world stage. But the competition is fierce, as the rising star from Russia, Yuri Plisetsky, is relentlessly determined to defeat Yuuri and win back Victor's tutelage.",
};

function MediaDescription() {
    const id = window.location.pathname.split('/')[3];
    console.log(id); //media id
    return (<div>
        <MediaDetails image={DUMMY_DETAILS.image} title={DUMMY_DETAILS.title}
                      releaseYear={DUMMY_DETAILS.releaseDate} countryName={DUMMY_DETAILS.country}
                      description={DUMMY_DETAILS.description}/>
    {/*    Aca irian listas para que contengan a la media*/}
    {/*    Aca iria comment Section*/}
    </div>)
}

export default MediaDescription;