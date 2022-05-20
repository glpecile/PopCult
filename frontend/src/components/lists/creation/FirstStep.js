import {useState} from "react";
import {useTranslation} from "react-i18next";
import LengthProgress from "../../comments/LengthProgress";

const FirstStep = (props) => {
    const {t} = useTranslation();

    const [listNameError, setListNameError] = useState(false);
    const [listDescriptionError, setListDescriptionError] = useState(false);

    const MAX_LENGTH = 100;
    const MAX_DESC_LENGTH = 1000;

    const listNameHandler = (event) => {
        event.target.validity.valid ? (event.target.value.length === 0 ? setListNameError(true) : setListNameError(false)) : setListNameError(true);
        props.setListName(event.target.value);
        if (event.target.validity.valid === false || event.target.value.length === 0 || listDescriptionError === true) props.setState(false)
        else props.setState(true);
    }

    const listDescriptionHandler = (event) => {
        let valid = /[^/><]+/.test(event.target.value) || event.target.value.length === 0;
        valid ? setListDescriptionError(false) : setListDescriptionError(true);
        if (valid) props.setListDescription(event.target.value);
        if (valid === false || listNameError === true || props.listName.length === 0) props.setState(false);
        else props.setState(true);
    }

    return (<div className="px-5 pt-3 text-semibold w-full">
        {/*list name input*/}
        <label
            className="py-2 text-semibold w-full after:content-['*'] after:ml-0.5 after:text-violet-400">
            {t('lists_listName')}
        </label>
        <input
            className={"rounded w-full bg-gray-50 mb-2 " + (listNameError ? "border-2 border-rose-500" : "")}
            type='text' value={props.listName}
            onChange={listNameHandler} pattern="[^/><]+" minLength={1} maxLength={100}/>
        <LengthProgress length={props.listName.length} max={MAX_LENGTH} text={t('length_count', {current: props.listName.length, max: MAX_LENGTH})}/>
        {/*description textarea*/}
        <label className="py-2 text-semibold w-full after:ml-0.5 after:text-violet-400">
            {t('lists_description')}
        </label>
        <textarea
            className={"rounded w-full bg-gray-50" + (listDescriptionError ? "border-2 border-rose-500" : "")}
            value={props.listDescription} onChange={listDescriptionHandler}/>
        <LengthProgress length={props.listDescription.length} max={MAX_DESC_LENGTH} text={t('length_count', {current: props.listDescription.length, max: MAX_DESC_LENGTH})}/>
    </div>);
}
export default FirstStep;