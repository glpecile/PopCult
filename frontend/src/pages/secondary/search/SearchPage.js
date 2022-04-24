import {useSearchParams} from "react-router-dom";

export default function SearchPage() {
    const [searchParams] = useSearchParams();
    const term = searchParams.get("term");
    return (<div>{term}</div>);
}