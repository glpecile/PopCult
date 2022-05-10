import MediaOptions from "./MediaOptions";
import ShareMenu from "./share/ShareMenu";
import Chips from "../Chips";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";

function MediaDetails(props) {
    const {t} = useTranslation();
    return (
        <div className="row">
            <Helmet>
                <title>
                    {props.title} • PopCult
                </title>
            </Helmet>
            <div className="col-12 col-lg-4">
                <div className="grid auto-rows-min shadow-md rounded-lg divide-y divide-slate-300 my-3 bg-white">
                    <img className="w-full rounded-t-lg" src={props.image} alt="Media Details"/>
                    <MediaOptions mediaData={props.mediaData}/>
                    <ShareMenu isOpened={false}/>
                    {/* TODO: Add to list component */}
                </div>
            </div>
            <div className="col-12 col-lg-8 mb-1.5">
                <h1 className="text-5xl font-black justify-start pt-2 break-words max-w-full tracking-wide">
                    {props.title}
                </h1>
                <div className="text-xl pb-2 tracking-wide">
                    <span>{props.releaseYear} • {props.countryName}</span>
                </div>
                <p className="lead tracking-tight text-justify">{props.description}</p>

                {/* Genres */}
                <h5 className="font-bold text-2xl py-2">
                    {t('media_genre')}
                </h5>
                <div className="flex flex-wrap gap-2">
                    {
                        props.genres.map(
                            (g) => {
                                return <Chips key={g.id} url='#' text={t('genre_'+g.genre.toLowerCase())}/>
                            }
                        )
                    }
                </div>

                {/* TODO: Studios */}
                <h5 className="font-bold text-2xl py-2 mt-3">
                    {t('media_studio')}
                </h5>
                <div className="flex flex-wrap gap-2">
                    <Chips text="😀😀😀 😀😀😀" url="#"/>
                </div>

                {/* TODO: Staff */}
                <h5 className="font-bold text-2xl py-2 mt-3">
                    {t('media_staff')}
                </h5>
                <div className="flex flex-wrap gap-2">
                    <Chips text="😀😀😀 😀😀😀" url="#"/>
                </div>
            </div>
            {/* TODO: Add List recommendations where the media is included */}
        </div>
    );
}

export default MediaDetails;