import Navbar from "../Navbar";
import Footer from "../Footer";

function Layout(props) {
    return (
        <div className="bg-gray-50">
            <div className="min-h-screen flex flex-col">
            <Navbar/>
                <div className="col-8 offset-2 flex-grow">
                    <main>{props.children}</main>
                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default Layout;