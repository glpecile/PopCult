import MediaOptions from "./MediaOptions";
import ShareMenu from "./share/ShareMenu";
import MediaChips from "./MediaChips";
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
                    <img className="w-full rounded-t-lg" src={`${props.image}?size=lg`} alt="Media Details"/>
                    <MediaOptions mediaData={props.mediaData}/>
                    <ShareMenu isOpened={false}/>
                    {
                        // TODO: Add to list component
                    }
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

                {
                    /**
                     * Genres
                     * -------------------
                     * genre: "COMEDY"
                     * id: 5
                     * listsUrl: "http://localhost:8080/webapp_war/api/genres/Comedy/lists"
                     * mediaUrl: "http://localhost:8080/webapp_war/api/genres/Comedy/media"
                     * url: "http://localhost:8080/webapp_war/api/genres/Comedy"
                     * [[Prototype]]: Object
                     */
                }
                <h5 className="font-bold text-2xl py-2">
                    {t('media_genre')}
                </h5>
                <div className="flex flex-wrap gap-2">
                    {
                        props.genres.map(
                            (g) => {
                                return <MediaChips key={g.id} url='#' text={t('genre_' + g.genre.toLowerCase())}/>
                            }
                        )
                    }
                </div>

                {
                    /**
                     * Studios
                     * -------------------
                     * id: 54
                     * imageUrl: "http://localhost:8080/webapp_war/api/studios/54/image"
                     * mediaUrl: "http://localhost:8080/webapp_war/api/studios/54/media"
                     * name: "Paramount"
                     * url: "http://localhost:8080/webapp_war/api/studios/5
                     */
                }
                <h5 className="font-bold text-2xl py-2 mt-3">
                    {t('media_studio')}
                </h5>
                <div className="flex flex-wrap gap-2">
                    {
                        props.studios.map(
                            (s) => {
                                return <MediaChips key={s.id} url="#" text={s.name}/>
                            }
                        )
                    }
                </div>

                {
                    /**
                     * Staff (crew & directors)
                     * -------------------
                     * description: "Idris Elba (born 6 September 1972) is a British television, theatre, and film actor who has starred in both British and American productions. One of his first acting roles was in the soap opera Family Affairs. Since then he has worked in a variety of TV and movie projects including Ultraviolet, The Wire, No Good Deed and Zootopia."
                     * id: 161
                     * imageUrl: "http://localhost:8080/webapp_war/api/staff/161/image"
                     * mediaActorUrl: "http://localhost:8080/webapp_war/api/staff/161/media?role=Actor"
                     * mediaDirectorUrl: "http://localhost:8080/webapp_war/api/staff/161/media?role=Director"
                     * mediaUrl: "http://localhost:8080/webapp_war/api/staff/161/media"
                     * name: "Idris Elba"
                     * url: "http://localhost:8080/webapp_war/api/staff/161"
                     */
                }
                <h5 className="font-bold text-2xl py-2 mt-3">
                    {t('media_staff')}
                </h5>
                <div className="flex flex-wrap gap-2">
                    {
                        props.directors &&
                        props.directors.map(
                            (d) => {
                                return (
                                    <MediaChips role="Director" key={d.id} url="#" text={d.name}/>
                                )
                            }
                        )
                    }
                    {
                        props.crew &&
                        props.crew.map(
                            (c) => {
                                return (
                                    <MediaChips role="Actor" key={c.id} url="#" text={c.name}/>
                                )
                            }
                        )
                    }
                </div>
            </div>
            {/* TODO: Add List recommendations where the media is included */}
        </div>
    );
}

export default MediaDetails;