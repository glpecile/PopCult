import OneButtonDialog from "../modal/OneButtonDialog";
import ClearIcon from "@mui/icons-material/Clear";
import CheckOutlinedIcon from "@mui/icons-material/CheckOutlined";
import {useTranslation} from "react-i18next";

const ReportButtons = (props) => {
    const {t} = useTranslation();

    return (<div className="flex justify-end items-end text-end mr-2 mb-2 space-x-2">
        <OneButtonDialog
            buttonClassName="btn btn-link btn-rounded text-violet-500 hover:text-violet-900 h-min flex items-center"
            buttonIcon={<CheckOutlinedIcon/>}
            buttonText={t('accept_report_title')}
            title={t('accept_report_title')}
            body={t('accept_report_body', {username: props.reporterUsername})}
            actionTitle={t('accept')}
            onActionAccepted={props.acceptReport}
            submitButtonClassName="btn btn-link btn-rounded text-violet-500 hover:text-violet-900"
            isOpened={false}/>
        <OneButtonDialog
            buttonClassName="btn btn-link btn-rounded text-amber-500 hover:text-amber-900 h-min flex items-center"
            buttonText={t('reject_report_title')}
            buttonIcon={<ClearIcon/>}
            title={t('reject_report_title')}
            body={t('reject_report_body', {username: props.reporterUsername})}
            actionTitle={t('reject')}
            onActionAccepted={props.rejectReport}
            submitButtonClassName="btn btn-link btn-rounded text-amber-500 hover:text-amber-900"
            isOpened={false}/>
    </div>);
}
export default ReportButtons;