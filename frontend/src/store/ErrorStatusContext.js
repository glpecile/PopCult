import {useLocation} from 'react-router-dom';
import {createContext, useEffect, useMemo, useState} from "react";
import Error404 from "../pages/secondary/errors/Error404";
import Layout from "../components/Layout/Layout";
import Error401 from "../pages/secondary/errors/Error401";
import Error500 from "../pages/secondary/errors/Error500";
import Error403 from "../pages/secondary/errors/Error403";
import Error400 from "../pages/secondary/errors/Error400";

const ErrorStatusContext = createContext({});

export const ErrorHandler = ({children}) => {
    const location = useLocation()
    const [errorStatusCode, setErrorStatusCode] = useState();

    useEffect(() => {
        return setErrorStatusCode(undefined);
    }, [location.pathname])

    const handleErrors = () => {
        if (errorStatusCode === 404) {
            return <Layout><Error404/></Layout>
        } else if (errorStatusCode === 401) {
            return <Layout><Error401/></Layout>
        } else if (errorStatusCode === 500) {
            return <Layout><Error500/></Layout>
        } else if (errorStatusCode === 403) {
            return <Layout><Error403/></Layout>
        } else if (errorStatusCode === 400) {
            return <Layout><Error400/></Layout>
        }
        return children;
    }

    const contextPayload = useMemo(
        () => ({setErrorStatusCode}),
        [setErrorStatusCode]
    );

    return (
        <ErrorStatusContext.Provider value={contextPayload}>
            {handleErrors()}
        </ErrorStatusContext.Provider>
    )
}

export default ErrorStatusContext;