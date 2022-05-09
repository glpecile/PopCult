import ListsSlider from "../../components/lists/ListsSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import useErrorStatus from "../../hooks/useErrorStatus";
import listService from "../../services/ListService";
import Loader from "../secondary/errors/Loader";

function Lists() {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const [carrouselLists, setCarrouselLists] = useState(undefined);
    const [lists, setLists] = useState(undefined);
    const [page, setPage] = useState(1);
    const {setErrorStatusCode} = useErrorStatus();

    useEffect(() => {
        async function getCarrouselLists() {
            try {
                const data = await listService.getLists({page: 1, pageSize: 12})
                setCarrouselLists(data.data);
            } catch (error) {
                setErrorStatusCode(error.response.status);
            }
        }

        getCarrouselLists();
    }, []);

    const createNewList = () => {
        navigate('/lists/new');
    }

    return (
        <section>
            <Helmet>
                <title>{t('lists_title')}</title>
            </Helmet>
            {carrouselLists ? <>
                <div className="flex flex-wrap justify-between p-2.5 pb-0">
                    <h4 className="font-bold text-2xl pt-2">
                        {t('lists_popular')}
                    </h4>
                    <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                            onClick={createNewList}>
                        {t('lists_newList')}
                    </button>
                </div>
                <ListsSlider lists={carrouselLists}/>
            </> : <Loader/>}
        </section>
    );
}

export default Lists;