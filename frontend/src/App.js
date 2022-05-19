import {HelmetProvider} from "react-helmet-async";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import React, {Suspense, lazy} from "react";
import Loader from "./pages/secondary/errors/Loader";
import Layout from './components/Layout/Layout';
import ModeratorRequest from "./pages/secondary/admin/ModeratorRequest";
import LoggedGate from "./components/permissions/LoggedGate";

const Films = lazy(() => import('./pages/primary/Films'));
const Series = lazy(() => import('./pages/primary/Series'));
const Lists = lazy(() => import('./pages/primary/Lists'));
const Home = lazy(() => import('./pages/primary/Home'));
const MediaDescription = lazy(() => import('./pages/secondary/media/MediaDescription'));
const ListsDescription = lazy(() => import('./pages/secondary/lists/ListsDescription'));
const Genres = lazy(() => import('./pages/secondary/genres/Genres'));
const Studio = lazy(() => import('./pages/secondary/studios/Studio'));
const StaffProfile = lazy(() => import('./pages/secondary/staff/StaffProfile'));
const Login = lazy(() => import('./pages/secondary/login/Login'));
const Recovery = lazy(() => import('./pages/secondary/login/Recovery'));
const ResetPassword = lazy(() => import('./pages/secondary/login/ResetPassword'));
const Register = lazy(() => import('./pages/secondary/login/Register'));
const SuccessfulRegister = lazy(() => import('./pages/secondary/login/SuccessfulRegister'));
const ExpiredToken = lazy(() => import('./pages/secondary/login/ExpiredToken'));
const Profile = lazy(() => import('./pages/secondary/user/Profile'));
const Settings = lazy(() => import('./pages/secondary/user/Settings'));
const Verification = lazy(() => import('./pages/secondary/login/Verification'));
const UserPanel = lazy(() => import('./pages/secondary/user/panel/UserPanel'));
const UserRequests = lazy(() => import('./pages/secondary/user/panel/UserRequests'));
const UserNotifications = lazy(() => import('./pages/secondary/user/panel/UserNotifications'));
const UserLists = lazy(() => import('./pages/secondary/user/UserLists'));
const AdminPanel = lazy(() => import('./pages/secondary/admin/AdminPanel'));
const Reports = lazy(() => import('./pages/secondary/admin/Reports'));
const BannedUsers = lazy(() => import('./pages/secondary/admin/BannedUsers'));
const Moderators = lazy(() => import('./pages/secondary/admin/Moderators'));
const Error404 = lazy(() => import('./pages/secondary/errors/Error404'));
const ListsCreation = lazy(() => import('./pages/secondary/lists/ListsCreation'));
const SearchPage = lazy(() => import('./pages/secondary/search/SearchPage'));
const ListsEdition = lazy(() => import('./pages/secondary/lists/ListsEdition'));

export default function App() {
    const helmetContext = {};

    return (
        <HelmetProvider context={helmetContext}>
            <BrowserRouter basename={process.env.PUBLIC_URL}>
                <Layout>
                    <Suspense fallback={<Loader/>}>
                        <Routes>
                            <Route path='/' element={<Home/>}/>
                            <Route path='/media/films' element={<Films/>}/>
                            <Route path='/media/series' element={<Series/>}/>
                            <Route path='/media/films/:id' element={<MediaDescription/>}/>
                            <Route path='/media/series/:id' element={<MediaDescription/>}/>
                            <Route path='/genres/:genre' element={<Genres/>}/>
                            <Route path='/studios/:id' element={<Studio/>}/>
                            <Route path='/staff/:id' element={<StaffProfile/>}/>
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
                            <Route path='/settings' element={<LoggedGate><Settings/></LoggedGate>}/>
                            <Route path='/verification' element={<Verification/>}/>
                            <Route path='/user/:username/panel' element={<UserPanel/>}/>
                            <Route path='/user/:username/requests' element={<UserRequests/>}/>
                            <Route path='/user/:username/notifications' element={<UserNotifications/>}/>
                            <Route path='/user/:username/lists' element={<LoggedGate><UserLists/></LoggedGate>}/>
                            <Route path='/admin' element={<AdminPanel/>}/>
                            <Route path='/admin/reports' element={<Reports/>}/>
                            <Route path='/admin/bans' element={<BannedUsers/>}/>
                            <Route path='/admin/mods' element={<Moderators/>}/>
                            <Route path='/search' element={<SearchPage/>}/>
                            <Route path='/requestMod' element={<LoggedGate><ModeratorRequest/></LoggedGate>}/>
                            <Route path='*' element={<Error404/>}/>
                        </Routes>
                    </Suspense>
                </Layout>
            </BrowserRouter>
        </HelmetProvider>
    );
}