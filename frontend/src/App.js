import React, {Suspense} from "react";
import {Route, Routes} from "react-router-dom";
import Films from "./pages/primary/Films";
import Series from "./pages/primary/Series";
import Lists from "./pages/primary/Lists";
import Home from "./pages/primary/Home";
import MediaDescription from "./pages/secondary/media/MediaDescription";
import ListsDescription from "./pages/secondary/lists/ListsDescription";
import Login from "./pages/secondary/login/Login";
import Register from "./pages/secondary/login/Register";
import Profile from "./pages/secondary/user/Profile";
import Settings from "./pages/secondary/user/Settings";
import Verification from "./pages/secondary/login/Verification";
import UserPanel from "./pages/secondary/user/UserPanel";
import UserLists from "./pages/secondary/user/UserLists";
import AdminPanel from "./pages/secondary/admin/AdminPanel";
import Error404 from "./pages/secondary/errors/Error404";
import Loader from "./pages/secondary/errors/Loader";
import Layout from "./components/Layout/Layout";

export default function App() {
    return (
        <Suspense fallback={<Loader/>}>
            <Layout>
                <Routes>
                    <Route path='/' exact element={<Home/>}/>
                    <Route path='/media/films' exact element={<Films/>}/>
                    <Route path='/media/series' exact element={<Series/>}/>
                    <Route path='/lists' exact element={<Lists/>}/>
                    <Route path='/media/films/:id' exact element={<MediaDescription/>}/>
                    <Route path='/media/series/:id' exact element={<MediaDescription/>}/>
                    <Route path='/lists/:id' exact element={<ListsDescription/>}/>
                    <Route path='/login' exact element={<Login/>}/>
                    <Route path='/register' exact element={<Register/>}/>
                    <Route path='/user/:username' exact element={<Profile/>}/>
                    <Route path='/settings' exact element={<Settings/>}/>
                    <Route path='/verification' exact element={<Verification/>}/>
                    <Route path='/user/:username/panel' exact element={<UserPanel/>}/>
                    <Route path='/user/:username/lists' exact element={<UserLists/>}/>
                    <Route path='/admin' exact element={<AdminPanel/>}/>
                    <Route path='/admin/reports' exact element={<AdminPanel/>}/>
                    <Route path='/admin/bans' exact element={<AdminPanel/>}/>
                    <Route path='/admin/mods' exact element={<AdminPanel/>}/>
                    <Route path='*' element={<Error404/>}/>
                </Routes>
            </Layout>
        </Suspense>
    );
}