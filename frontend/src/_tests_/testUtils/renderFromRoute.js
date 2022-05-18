import React, {Suspense} from "react";
import Loader from "../../pages/secondary/errors/Loader";
import Layout from "../../components/Layout/Layout";
import {MemoryRouter, Route, Routes} from "react-router-dom";
import {render} from '@testing-library/react';
import Home from "../../pages/primary/Home";
import Films from "../../pages/primary/Films";
import Series from "../../pages/primary/Series";
import MediaDescription from "../../pages/secondary/media/MediaDescription";
import Lists from "../../pages/primary/Lists";
import ListsDescription from "../../pages/secondary/lists/ListsDescription";
import ListsEdition from "../../pages/secondary/lists/ListsEdition";
import LoggedGate from "../../components/permissions/LoggedGate";
import ListsCreation from "../../pages/secondary/lists/ListsCreation";
import Login from "../../pages/secondary/login/Login";
import Recovery from "../../pages/secondary/login/Recovery";
import ResetPassword from "../../pages/secondary/login/ResetPassword";
import Register from "../../pages/secondary/login/Register";
import SuccessfulRegister from "../../pages/secondary/login/SuccessfulRegister";
import ExpiredToken from "../../pages/secondary/login/ExpiredToken";
import Profile from "../../pages/secondary/user/Profile";
import Settings from "../../pages/secondary/user/Settings";
import Verification from "../../pages/secondary/login/Verification";
import UserPanel from "../../pages/secondary/user/panel/UserPanel";
import UserRequests from "../../pages/secondary/user/panel/UserRequests";
import UserNotifications from "../../pages/secondary/user/panel/UserNotifications";
import UserLists from "../../pages/secondary/user/UserLists";
import AdminPanel from "../../pages/secondary/admin/AdminPanel";
import Reports from "../../pages/secondary/admin/Reports";
import BannedUsers from "../../pages/secondary/admin/BannedUsers";
import Moderators from "../../pages/secondary/admin/Moderators";
import SearchPage from "../../pages/secondary/search/SearchPage";
import Error404 from "../../pages/secondary/errors/Error404";
import {HelmetProvider} from "react-helmet-async";


// https://v5.reactrouter.com/web/api/MemoryRouter

const renderFromRoute = (path) => {
    const helmetContext = {};

    render(
        <React.StrictMode>
            <HelmetProvider context={helmetContext}>
                <MemoryRouter initialEntries={[path]}>
                    <Suspense fallback={<Loader/>}>
                        <Layout>
                            <Routes>
                                <Route path='/' element={<Home/>}/>
                                <Route path='/media/films' element={<Films/>}/>
                                <Route path='/media/series' element={<Series/>}/>
                                <Route path='/media/films/:id' element={<MediaDescription/>}/>
                                <Route path='/media/series/:id' element={<MediaDescription/>}/>
                                <Route path='/lists' element={<Lists/>}/>
                                <Route path='/lists/:id' element={<ListsDescription/>}/>
                                <Route path='/lists/:id/edit' element={<ListsEdition/>}/>
                                <Route path='/lists/new' element={<LoggedGate><ListsCreation/></LoggedGate>}/>
                                <Route path='/login' element={<Login/>}/>
                                <Route path='/recovery' element={<Recovery/>}/>
                                <Route path='/resetPassword' element={<ResetPassword/>}/>
                                <Route path='/register' element={<Register/>}/>
                                <Route path='/register/success' element={<SuccessfulRegister/>}/>
                                <Route path='/register/expired' element={<ExpiredToken/>}/>
                                <Route path='/user/:username' element={<Profile/>}/>
                                <Route path='/settings' element={<Settings/>}/>
                                <Route path='/verification' element={<Verification/>}/>
                                <Route path='/user/:username/panel' element={<UserPanel/>}/>
                                <Route path='/user/:username/requests' element={<UserRequests/>}/>
                                <Route path='/user/:username/notifications' element={<UserNotifications/>}/>
                                <Route path='/user/:username/lists' element={<UserLists/>}/>
                                <Route path='/admin' element={<AdminPanel/>}/>
                                <Route path='/admin/reports' element={<Reports/>}/>
                                <Route path='/admin/bans' element={<BannedUsers/>}/>
                                <Route path='/admin/mods' element={<Moderators/>}/>
                                <Route path='/search' element={<SearchPage/>}/>
                                <Route path='*' element={<Error404/>}/>
                            </Routes>
                        </Layout>
                    </Suspense>
                </MemoryRouter>
            </HelmetProvider>
        </React.StrictMode>
    )
}

export default renderFromRoute