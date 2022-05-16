import {Link} from "react-router-dom";
import {Trans} from "react-i18next";

const CompactListsCard = (props) => {
    const list = props.fork;

    return <div
        className={"w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107 " + props.className}>
        <div className="flex items-center">
            <Link className="pl-3 py-4 text-xl font-semibold tracking-tight text-gray-800"
                  to={'/lists/' + list.id}>{list.name}</Link>
            <div className="pl-1 justify-end">
                <Trans i18nKey="list_by">
                    <Link className="text-violet-500 hover:text-violet-900 font-bold"
                          to={`/user/${list.owner}`}>{{username: list.owner}}</Link>
                </Trans>
            </div>
        </div>
    </div>;
}
export default CompactListsCard;