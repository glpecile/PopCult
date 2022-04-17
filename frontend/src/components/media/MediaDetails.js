import MediaOptions from "./MediaOptions";
import ShareMenu from "./share/ShareMenu";
import {Helmet} from "react-helmet-async";

function MediaDetails(media) {
    return (
        <div className="row">
            <Helmet>
                <title>
                    {media.title} • PopCult
                </title>
            </Helmet>
            <div className="col-12 col-lg-4">
                <div className="grid auto-rows-min shadow-md rounded-lg divide-y divide-slate-300 my-3 bg-white">
                    <img className="w-full rounded-t-lg" src={media.image} alt="Media Details"/>
                    <MediaOptions/>
                    <ShareMenu isOpened={false}/>
                    {/* TODO: Add to list component */}
                </div>
            </div>
            <div className="col-12 col-lg-8 mb-1.5">
                <h1 className="text-5xl font-black justify-start pt-2 break-words max-w-full tracking-wide">
                    {media.title}
                </h1>
                <div className="text-xl pb-2 tracking-wide">
                    <span>{media.releaseYear} • {media.countryName}</span>
                </div>
                <p className="lead tracking-tight text-justify">{media.description}</p>
                {/* TODO: Add chips with genre, production companies, director & cast */}
            </div>
            {/* TODO: Add List recommendations where the media is included */}
        </div>
    );
}

export default MediaDetails;