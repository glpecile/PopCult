import MediaOptions from "./MediaOptions";
import ShareMenu from "./share/ShareMenu";
import MediaChips from "./MediaChips";
import {Helmet} from "react-helmet-async";
import {useTranslation} from "react-i18next";

function MediaDetails(props) {
    const {t} = useTranslation();
    return (
        <div className="flex flex-col lg:flex-row gap-4">
            <Helmet>
                <title>
                    {props.title} • PopCult
                </title>
            </Helmet>

            <div className="flex flex-col basis-1/3">
                <div className="shadow-md rounded-lg divide-y divide-slate-300 my-3 bg-white">
                    <img className="w-full object-center rounded-t-lg" src={`${props.image}?size=lg`} alt="Media Details"/>
                    <MediaOptions mediaData={props.mediaData}/>
                    <ShareMenu isOpened={false}/>
                    {
                        // TODO: Add to list component
                    }
                </div>
            </div>

            <div className="basis-3/4">
                <h1 className="text-5xl font-black justify-start pt-2 break-words max-w-full tracking-wide">
                    {props.title}
                </h1>
                <div className="text-xl pb-2 tracking-wide">
                    <span>{props.releaseYear} • {props.countryName}</span>
                </div>
                <p className="lead tracking-tight text-justify">
                    {props.description}
                </p>
                {
                    /**
                     * Genres
                     * -------------------
                     * @type
                     * - Object
                     * @properties
                     * - genre
                     * - id
                     * - listsUrl
                     * - mediaUrl
                     * - url
                     */
                    props.genres && <>
                        <h5 className="font-bold text-2xl py-2">
                            {t('media_genre')}
                        </h5>
                        <div className="flex flex-wrap gap-2">
                            {
                                props.genres.map(
                                    (g) => {
                                        let genre = g.genre.toLowerCase()
                                        return <MediaChips key={g.id} url={'/genres/' + genre} text={t('genre_' + genre)}/>
                                    }
                                )
                            }
                        </div>
                    </>}

                {
                    /**
                     * Studios
                     * -------------------
                     * @type
                     * - Object
                     * @properties
                     * - id
                     * - imageUrl
                     * - mediaUrl
                     * - name
                     * - url
                     */
                    props.studios &&
                    <>
                        <h5 className="font-bold text-2xl py-2 mt-3">
                            {t('media_studio')}
                        </h5>
                        <div className="flex flex-wrap gap-2">
                            {
                                props.studios.map(
                                    (s) => {
                                        return <MediaChips key={s.id} url={'/studios/' + s.id} text={s.name}/>
                                    }
                                )
                            }
                        </div>
                    </>}

                {
                    /**
                     * Staff (crew & directors)
                     * -------------------
                     * @type
                     * - Object
                     * @properties
                     * - description
                     * - id
                     * - imageUrl
                     * - mediaActorUrl
                     * - mediaDirectorUrl
                     * - mediaUrl
                     * - name
                     * - url
                     */
                    (props.directors || props.crew) && <>
                        <h5 className="font-bold text-2xl py-2 mt-3">
                            {t('media_staff')}
                        </h5>
                        <div className="flex flex-wrap gap-2">
                            {
                                props.directors &&
                                props.directors.map(
                                    (d) => {
                                        return (
                                            <MediaChips role="Director" key={d.id} url={'/staff/' + d.id} text={d.name}/>
                                        )
                                    }
                                )
                            }
                            {
                                props.crew &&
                                props.crew.map(
                                    (c) => {
                                        return (
                                            <MediaChips role="Actor" key={c.id} url={'/staff/' + c.id} text={c.name}/>
                                        )
                                    }
                                )
                            }
                        </div>
                    </>}
            </div>
        </div>
    );
}

export default MediaDetails;