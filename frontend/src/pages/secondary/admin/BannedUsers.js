import {useCallback, useEffect, useRef, useState} from "react";
import UserService from "../../../services/UserService";
import BannedUserCard from "../../../components/admin/BannedUserCard";
import {useTranslation} from "react-i18next";
import Spinner from "../../../components/animation/Spinner";
import NothingToShow from "../../../components/admin/NothingToShow";
import RolesGate from "../../../components/permissions/RolesGate";
import {Roles} from "../../../enums/Roles";
import PaginationComponent from "../../../components/PaginationComponent";
import * as React from "react";
import {Helmet} from "react-helmet-async";
import ErrorSnackbar from "../../../components/ErrorSnackbar";

const BannedUsers = () => {
    // const query = new URLSearchParams(useLocation().search);
    const [page, setPage] = useState(1); // set state queda para paginacion
    const [bannedUsers, setBannedUsers] = useState(undefined);
    const [refresh, setRefresh] = useState(false);
    const bannedUsersMounted = useRef(true);
    const [error, setError] = useState(false);

    const {t} = useTranslation();

    const getBannedUsers = useCallback(async () => {
        if (bannedUsersMounted.current) try {
            const users = await UserService.getBannedUsers({page, pageSize: 12});
            setBannedUsers(users);
        } catch (error) {
            setError(true)
        }
    }, [page]);

    const unbanUser = useCallback(async (url) => {
        try {
            await UserService.unbanUser(url);
            setRefresh((prevState => prevState + 1));
        } catch (error) {
            setError(true);
        }
    }, []);

    useEffect(() => {
        bannedUsersMounted.current = true;
        getBannedUsers();
        return () => {
            bannedUsersMounted.current = false;
        }
    }, [getBannedUsers, refresh]);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setError(false);
        }, 5000);

        return () => clearTimeout(timeout);
    },[error]);

    return (<RolesGate level={Roles.MOD}>
        <Helmet>
            <title>{t('banned_users_title')}</title>
        </Helmet>
        <h1 className="text-3xl fw-bolder fw-bolder text-center py-4">
            {t('banned_users')}
        </h1>
        {bannedUsers === undefined ? <Spinner/> : bannedUsers.data.length === 0 ?
            <NothingToShow text={t('banned_users_empty')}/> : <>{(bannedUsers.data.map(user => {
                return <BannedUserCard key={user.username} username={user.username} strikes={user.strikes}
                                       unbanDate={user.unbanDate}
                                       image={user.imageUrl}
                                       url={user.lockedUrl}
                                       unbanUser={unbanUser}
                />;
            }))}
                <div className="flex justify-center pt-4">
                    {(bannedUsers.data.length > 0 && bannedUsers.links.last.page > 1) &&
                        <PaginationComponent page={page} lastPage={bannedUsers.links.last.page}
                                             setPage={setPage}/>
                    }
                </div>
            </>}
        <ErrorSnackbar show={error} text={t('error400_body')}/>
    </RolesGate>);

}
export default BannedUsers;