import ListsSlider from "../../components/lists/ListsSlider";
import {useTranslation} from "react-i18next";
import {Helmet} from "react-helmet-async";
import {useNavigate} from "react-router-dom";

const DUMMY_DATA = [{
    id: 1,
    image1: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
    listTitle: "Yuri's List",
    releaseDate: "2016",
}];

function Lists() {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const createNewList = () => {
        navigate('/lists/new');
    }

    return (
        <section>
            <Helmet>
                <title>{t('lists_title')}</title>
            </Helmet>
            <div className="flex flex-wrap justify-between p-2.5 pb-0">
                <h4 className="font-bold text-2xl pt-2">
                    {t('lists_popular')}
                </h4>
                <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded"
                        onClick={createNewList}>
                    {t('lists_newList')}
                </button>
            </div>
            <ListsSlider media={DUMMY_DATA}/>
        </section>
    );
}

export default Lists;