import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import './i18n';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.js';
import '@fortawesome/fontawesome-free/js/all.js'
import "react-alice-carousel/lib/alice-carousel.css";
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import '@js-joda/core'
import {createTheme, ThemeProvider} from "@mui/material";

const theme = createTheme({
    palette: {
        primary: {
            main: 'rgb(139 92 246)'
        },
        secondary: {
            main: 'rgb(139 92 246)'
        }
    }
});

ReactDOM.render(
    <React.StrictMode>
            <ThemeProvider theme={theme}>
                <App/>
            </ThemeProvider>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
