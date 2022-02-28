import {useCallback, useEffect, useRef, useState} from "react";
import {useLocation} from "react-router-dom";
import UserService from "../../../services/UserService";
import Loader from "../errors/Loader";
import BannedUserCard from "../../../components/admin/BannedUserCard";
import {useTranslation} from "react-i18next";

const BannedUsers = () => {
    const query = new URLSearchParams(useLocation().search);
    const page = query.get('page') || 1;
    const pageSize = query.get('page-size') || 2;
    const [bannedUsers, setBannedUsers] = useState(undefined);
    const [refresh, setRefresh] = useState(false);
    const bannedUsersMounted = useRef(true);
    const {t} = useTranslation();

    const getBannedUsers = useCallback(async () => {
        if (bannedUsersMounted.current)
            try {
                const users = await UserService.getBannedUsers({page, pageSize});
                setBannedUsers(users);
            } catch (error) {
                console.log(error);
            }
    }, [page, pageSize]);

    const unbanUser = useCallback(async (username) => {
        try {
            await UserService.unbanUser(username);
            setRefresh((prevState => !prevState));
        } catch (error) {
            console.log(error);
        }
    }, []);

    useEffect(() => {
        bannedUsersMounted.current = true;
        getBannedUsers();
        return () => {
            bannedUsersMounted.current = false;
        }
    }, [getBannedUsers, refresh]);

    return (<>
        <h1 className="text-3xl fw-bolder fw-bolder py-4">
            Banned Users
        </h1>
        {bannedUsers === undefined && <Loader/>}
        {bannedUsers !== undefined && bannedUsers.data.length === 0 &&
            <div className="flex-col flex-wrap p-4 space-x-4">
                <img className="w-36 object-center mx-auto" src={require("../../../images/PopCultLogoExclamation.png")}
                     alt="no_results_image"/>
                <h3 className="text-xl text-gray-400 py-2 mt-3 text-center">
                    {t('banned_users_empty')}
                </h3>
            </div>}
        {bannedUsers !== undefined && bannedUsers.data.length !== 0 && (bannedUsers.data.map(user => {
            return <BannedUserCard key={user.username} username={user.username} strikes={user.strikes}
                                   unbanDate={user.banDate}
                                   image={user.imageUrl}
                                   unbanUser={unbanUser}
            />;
        }))}
    </>);

}
export default BannedUsers;