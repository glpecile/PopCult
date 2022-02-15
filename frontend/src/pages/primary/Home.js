import Loader from "../secondary/errors/Loader";
import MediaServices from "../../services/MediaServices";

export default function Home() {
    const hello = MediaServices();
    console.log(hello)
    return <Loader/>;
}