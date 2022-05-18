import {Trans} from "react-i18next";
import {Link} from "react-router-dom";
import useErrorStatus from "../../hooks/useErrorStatus";
import ReportService from "../../services/ReportService";
import ReportButtons from "./ReportButtons";


const ListCommentReport = (props) => {
    const {setErrorStatusCode} = useErrorStatus();

    const rejectReport = async () => {
        try{
            await ReportService.deleteListCommentReport(props.url)
        }catch (error){
            setErrorStatusCode(error.response.status);
        }
        props.refresh();
    }

    const acceptReport = async () => {
        try{
            await ReportService.approveListCommentReport(props.url)
        }catch (error){
            setErrorStatusCode(error.response.status);
        }
        props.refresh();
    }

    return <div
        className="my-2 w-full h-30 bg-white overflow-hidden rounded-lg shadow-md flex flex-col transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107">
        <h4 className="text-base text-xl py-2 pl-4 font-normal tracking-tight">
            <Trans i18nKey="report_comment_list">
                <Link className="text-violet-500 hover:text-violet-900 font-bold"
                      to={`/user/${props.reporterUsername}`}>{{reporterUsername: props.reporterUsername}}</Link>
                <Link className="text-violet-500 hover:text-violet-900 font-bold"
                      to={`/user/${props.reportedUsername}`}>{{reportedUsername: props.reportedUsername}}</Link>
                {{comment: props.comment}}
                <Link className="text-violet-500 hover:text-violet-900 font-bold"
                      to={`/lists/${props.listId}`}>{{list: props.listname}}</Link>
            </Trans>
        </h4>
        <div className="text-base pl-4 tracking-tight flex flex-wrap whitespace-pre-wrap">
            <Trans i18nKey="report_body">
                {{reportBody: props.reportBody}}
            </Trans>
        </div>
        <ReportButtons reporterUsername={props.reporterUsername} rejectReport={rejectReport} acceptReport={acceptReport}/>
    </div>;
}

export default ListCommentReport;