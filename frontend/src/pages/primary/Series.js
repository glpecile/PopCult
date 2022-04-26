import MediaSlider from "../../components/media/MediaSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useEffect, useState} from "react";
import MediaService from "../../services/MediaService";
import Loader from "../secondary/errors/Loader";


function Series() {
    const [t] = useTranslation();

    const [seriesData, setSeriesData] = useState(undefined);

    useEffect(() => {
        const getSeriesData = async () => {
            // TODO: Proper pagination
            let data = await MediaService.getSeries({});
            console.log(data);
            setSeriesData(data);
        };
        getSeriesData();
    }, [])

    return (
        <section>
            <Helmet>
                <title>{t('series_title')}</title>
            </Helmet>
            {
                !seriesData ? <Loader/> :
                    <>
                        <h4 className="font-bold text-2xl pt-2">
                            {t('series_popular')}
                        </h4>
                        <MediaSlider media={seriesData.data}/>
                    </>
            }
        </section>
    );
}

export default Series;