import {useCallback, useEffect, useRef, useState} from "react";
import {useLocation} from "react-router-dom";
import UserService from "../../../services/UserService";
import BannedUserCard from "../../../components/admin/BannedUserCard";
import {useTranslation} from "react-i18next";
import Spinner from "../../../components/animation/Spinner";
import NothingToShow from "../../../components/admin/NothingToShow";

const BannedUsers = () => {
    const query = new URLSearchParams(useLocation().search);
    const [page, setPage] = useState(query.get('page') || 1); // set state queda para paginacion
    const [pageSize, setPageSize] = useState(query.get('page-size') || 2);
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

    const unbanUser = useCallback(async (url) => {
        try {
            await UserService.unbanUser(url);
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
        <h1 className="text-3xl fw-bolder fw-bolder text-center py-4">
            {t('banned_users')}
        </h1>
        {bannedUsers === undefined && <Spinner/>}
        {bannedUsers !== undefined && bannedUsers.data.length === 0 && <NothingToShow text={t('banned_users_empty')}/>}
        {bannedUsers !== undefined && bannedUsers.data.length !== 0 && (bannedUsers.data.map(user => {
            return <BannedUserCard key={user.username} username={user.username} strikes={user.strikes}
                                   unbanDate={user.banDate}
                                   image={user.imageUrl}
                                   url={user.lockedUrl}
                                   unbanUser={unbanUser}
            />;
        }))}
    </>);

}
export default BannedUsers;