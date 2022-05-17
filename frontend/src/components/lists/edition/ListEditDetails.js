import {Checkbox, FormControlLabel} from "@mui/material";
import {useTranslation} from "react-i18next";

const ListEditDetails = (props) => {
    const {t} = useTranslation();
    const publicLabel = t('list_public');
    const collaborativeLabel = t('list_collaborative');

    const handleListName = (event) => {
        if (event.target.validity.valid) {
            props.setListName(event.target.value);
        }
    }
    const handleListDescription = (event) => {
        const aux = event.target.value;
        if (!(aux.includes('<') || aux.includes('>'))) props.setListDescription(aux);
    }

    const handlePublic = () => {
        props.setIsPublic(!props.isPublic)
    }

    const handleCollaborative = () => {
        props.setCollaborative(!props.isCollaborative)
    }

    return <>
        {props.isOwner ? <>
            <div className="flex flex-wrap pt-2">
                <label
                    className="py-2 font-semibold text-xl w-full after:content-['*'] after:ml-0.5 after:text-violet-400">
                    List title
                </label>
                <input className="rounded w-full bg-gray-50 text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide"
                       type='text' value={props.listName}
                       pattern='[^<\/>]+'
                       onChange={handleListName}/>
            </div>
            <label
                className="py-2 font-semibold text-xl w-full mt-2">
                Description
            </label>
            <textarea
                className="rounded w-full bg-gray-50 lead text-justify w-full max-w-full break-words mb-2"
                value={props.listDescription} onChange={handleListDescription} placeholder={t('lists_description')}/>

            <div className="flex justify-between pt-1 px-2">
                <FormControlLabel
                    control={<Checkbox checked={props.isPublic} color="secondary" onChange={handlePublic}/>}
                    label={publicLabel}/>
                <FormControlLabel control={<Checkbox checked={props.isCollaborative} color="secondary"
                                                     onChange={handleCollaborative}/>}
                                  label={collaborativeLabel}/>
            </div>
        </> : <>
            <div className="flex flex-wrap pt-2">
                <h2 className="text-5xl font-black capitalize justify-start pt-2 break-words max-w-full tracking-wide">
                    {props.listName}
                </h2>
            </div>
            <p className="lead text-justify max-w-full break-words pb-2">
                {props.listDescription}
            </p>
        </>}
    </>;
}
export default ListEditDetails;