import {Helmet} from "react-helmet-async";
import IconCard from "../../../../components/IconCard";
import {useTranslation} from "react-i18next";
import {useNavigate, useParams} from "react-router-dom";
import GroupOutlinedIcon from '@mui/icons-material/GroupOutlined';
import ForumOutlinedIcon from '@mui/icons-material/ForumOutlined';

const UserPanel = () => {
    const {t} = useTranslation();
    let {username} = useParams();
    const navigate = useNavigate();


    return (<>
        <Helmet>
            <title>{t('user_panel_title', {username: username})}</title>
        </Helmet>
        {/*Title*/}
        <h1 className="text-center text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide py-4">
            {t('user_panel_title', {username: username})}
        </h1>
        <div className="grid lg:grid-cols-2 grid-cols-1 gap-5">
            {/* Requests */}
            <IconCard icon={<GroupOutlinedIcon fontSize="large"
                                               className="text-violet-500 group-hover:text-violet-900 mt-3"/>}
                      title={t('user_panel_requests')}
                      description={t('user_panel_requests_detail')}
                      onClick={() => {
                          navigate(`/user/${username}/requests`)
                      }}/>
            {/* Comments */}
            <IconCard
                icon={<ForumOutlinedIcon fontSize="large"
                                         className="text-violet-500 group-hover:text-violet-900 mt-3"/>}
                title={t('user_panel_comments')}
                description={t('user_panel_comments_detail')}
                onClick={() => {
                    navigate(`/user/${username}/notifications`)
                }}/>
        </div>
    </>);
}
export default UserPanel;