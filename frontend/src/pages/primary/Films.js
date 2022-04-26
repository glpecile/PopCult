import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useEffect, useState} from "react";
import MediaService from "../../services/MediaService";
import Loader from "../secondary/errors/Loader";
import MediaSlider from "../../components/media/MediaSlider";

export default function Films() {
    const {t} = useTranslation();

    const [filmData, setFilmData] = useState(undefined);

    useEffect(() => {
        const getFilmData = async () => {
            // TODO: Proper pagination
            let data = await MediaService.getFilms({});
            console.log(data);
            setFilmData(data);
        };
        getFilmData();
    }, []);

    return (
        <section>
            <Helmet>
                <title>{t('films_title')}</title>
            </Helmet>
            {
                !filmData ? <Loader/> :
                    <>
                        <h4 className="font-bold text-2xl pt-2">
                            {t('films_popular')}
                        </h4>
                        <MediaSlider media={filmData.data}/>
                    </>
            }
        </section>
    );
}