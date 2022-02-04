import Navbar from "../Navbar";
import Footer from "../Footer";
import {useLocation} from "react-router-dom";
import {useEffect, useState} from "react";

function Layout(props) {
    const location = useLocation()
    const [layoutVisibility, setLayoutVisibility] = useState(true);

    useEffect(() => {
        setLayoutVisibility(location.pathname !== '/login');
        console.log("Location changed page 1: ", location);
    }, [location]);

    return <>
        {layoutVisibility &&
            (<div className="bg-gray-50">
                <div className="flex flex-col justify-between h-screen">
                    <Navbar/>
                    <main className="col-8 offset-2 flex-grow mb-auto">
                        {props.children}
                    </main>
                    <Footer/>
                </div>
            </div>)
        }
        {!layoutVisibility && <main>{props.children}</main>}
    </>
}

export default Layout;