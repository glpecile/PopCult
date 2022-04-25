import ListsCard from "../../../components/lists/ListsCard";
import {Helmet} from "react-helmet-async";
import {useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";

const DUMMY_DATA = [{
    id: 1,
    image1: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5WEM4N8RkRVf2HCBRErWP1yxb9NThPlwzrh794zmMhD9uN0i5",
    listTitle: "Yuri's List",
    releaseDate: "2016",
}];


const UserLists = () => {
    let {username} = useParams();
    const {t} = useTranslation();
    const navigate = useNavigate();

    const createNewList = () => {
        navigate('/lists/new');
    }

    const createListCover = (content) => {
        return (<div className="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3"
                     data-slider-target="image"
                     key={content.id}><ListsCard id={content.id} key={content.id} image1={content.image1}
                                                 image2={content.image1} image3={content.image1} image4={content.image1}
                                                 listTitle={content.listTitle}/></div>);
    }

    return (<>
            <Helmet>
                <title>{t('user_lists_header', {username: username})} &#8226; PopCult</title>
            </Helmet>
            <div className="row">
                <div className="flex flex-wrap justify-between p-2.5 pb-0">
                    <h2 className="text-3xl fw-bolder py-2">
                        {t('user_lists_title')}
                    </h2>
                    <button className="btn btn-link my-2.5 text-violet-500 hover:text-violet-900 btn-rounded" onClick={createNewList}>
                        {t('lists_newList')}
                    </button>
                    <div className="row py-2">
                        {DUMMY_DATA.length === 0 &&
                            <h3 className="text-center text-gray-400">
                                {t('user_lists_empty')}
                            </h3>}
                        {DUMMY_DATA.map(content => createListCover(content))}
                    </div>
                </div>
            </div>
        </>
    );
}
export default UserLists;