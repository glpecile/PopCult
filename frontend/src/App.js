import './App.css';
import {Route, Routes} from "react-router-dom";
import Films from "./pages/Films";
import Series from "./pages/Series";
import Lists from "./pages/Lists";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";

function App() {
    return (
        <div>
            <Navbar/>
            <Routes>
                <Route path='/' exact element={<Home/>}/>
                <Route path='/media/films' exact element={<Films/>}/>
                <Route path='/media/series' exact element={<Series/>}/>
                <Route path='/lists' exact element={<Lists/>}/>
            </Routes>
        </div>
    );
}

export default App;

// <div className="App">
//     <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo"/>
//         <p>
//             Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//             className="App-link"
//             href="https://reactjs.org"
//             target="_blank"
//             rel="noopener noreferrer"
//         >
//             Learn React
//         </a>
//     </header>
// </div>