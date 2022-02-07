import {Route, Routes} from "react-router-dom";
import Films from "./pages/primary/Films";
import Series from "./pages/primary/Series";
import Lists from "./pages/primary/Lists";
import Home from "./pages/primary/Home";
import MediaDescription from "./pages/secondary/media/MediaDescription";
import ListsDescription from "./pages/secondary/lists/ListsDescription";
import Login from "./pages/secondary/login/Login";
import Profile from "./pages/secondary/user/Profile";
import Settings from "./pages/secondary/user/Settings";
import Error404 from "./pages/secondary/errors/Error404";
import Layout from "./components/Layout/Layout";

function App() {
    return (
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
                <Route path='/user/:username' exact element={<Profile/>}/>
                <Route path='/settings' exact element={<Settings/>}/>
                <Route path='*' element={<Error404/>}/>
            </Routes>
        </Layout>
    );
}

export default App;