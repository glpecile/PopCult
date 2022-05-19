import BrandingImg from "../../../components/login/BrandingImg";
import {useTranslation} from "react-i18next";
import {useContext, useEffect, useState} from "react";
import Spinner from "../../../components/animation/Spinner";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";

import {Link} from "react-router-dom";
import userService from "../../../services/UserService";
import UserContext from "../../../store/UserContext";

export default function ModeratorRequest() {
    const [successfulRequest, setSuccessfulRequest] = useState(undefined);
    const {t} = useTranslation();
    const user = useContext(UserContext).user;

    useEffect(() => {
        const sendModeratorRequest = async () => {
            try {
                await userService.createModRequest(user.modRequestUrl);
                setSuccessfulRequest(true);
            } catch (error) {
                setSuccessfulRequest(false);
            }
        }

        if (user) {
            sendModeratorRequest();
        }
    }, [user]);

    return (<>
        <BrandingImg/>
        <div className="flex-grow mt-3">
            <div className="w-full max-w-sm mx-auto p-2.5 font-sans rounded-lg shadow-lg bg-white">
                {successfulRequest === undefined ?
                    <div className="flex justify-center flex-col">
                        <h2 className="text-3xl m-3 text-center">
                            {t('moderator_request_wait')}
                        </h2>
                        <Spinner/>
                    </div>
                    :
                    <>{successfulRequest === true ?
                        <div className="flex justify-center flex-col">
                            <h2 className="text-3xl m-3 text-center">
                                {t('moderator_request_sent_title')}
                            </h2>
                            <div className="whitespace-pre-wrap m-3 mb-0">
                                {t('moderator_request_sent_body')}
                            </div>
                        </div>
                        :
                        <div className="flex justify-center flex-col">
                            <h2 className="text-3xl m-3 text-center">
                                {t('moderator_request_error_title')}
                            </h2>
                            <div className="whitespace-pre-wrap m-3 mb-0">
                                {t('moderator_request_error_body')}
                            </div>
                        </div>
                    }

                        <div className="flex flex-row justify-between">
                            <div className="whitespace-pre-wrap mx-3 mb-1 mb-0">
                                {t('expiredToken_greet')}
                            </div>
                            <Link className="flex justify-end items-end" to='/'>
                                <button className="btn btn-link text-violet-500 hover:text-violet-900 btn-rounded">
                                    <HomeOutlinedIcon/> {t('nav_home')}
                                </button>
                            </Link>
                        </div>
                    </>

                }
            </div>
        </div>
    </>);
}