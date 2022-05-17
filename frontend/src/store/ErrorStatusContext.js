import {useLocation} from 'react-router-dom';
import {createContext, useEffect, useMemo, useState} from "react";
import Error404 from "../pages/secondary/errors/Error404";
import Layout from "../components/Layout/Layout";

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