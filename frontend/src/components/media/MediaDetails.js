import MediaOptions from "./MediaOptions";

function MediaDetails(media) {
    return (
        <div className="row">
            <div className="col-12 col-lg-4">
                <div className="grid auto-rows-min shadow-md rounded-lg divide-y divide-slate-300 my-3 bg-white">
                    <img className="img-fluid rounded-t-lg" src={media.image} alt="Media Details"/>
                    {/*    aca va la parte del cuadro de compartir, likear, etc, etc -> de esto hacmos otro componente*/}
                    <MediaOptions />
                </div>
            </div>
            <div className="col-12 col-lg-8 mb-1.5">
                <h1 className="display-5 fw-bolder justify-start pt-2 break-words max-w-full">
                    {media.title}
                </h1>
                <div className="text-xl py-2">
                    <span>{media.releaseYear}</span>
                    <span className="mx-1.5 mt-3">&#8226;</span>
                    <span>{media.countryName}</span>
                </div>
                <p className="lead text-justify">{media.description}</p>
            {/*    Aca van los chips con los actores, etc etc -> tambien otro componente*/}
            </div>
        </div>
    );
}

export default MediaDetails;