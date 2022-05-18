import {Link} from "react-router-dom";
import {Trans} from "react-i18next";

const CompactListsCard = (props) => {
    const list = props.fork;

    return <div
        className={"sm:w-[32rem] w-96 h-20 bg-white overflow-hidden rounded-lg shadow-md hover:shadow-indigo-500/50 flex justify-between transition duration-300 ease-in-out hover:bg-violet-50/50 " + props.className}>
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