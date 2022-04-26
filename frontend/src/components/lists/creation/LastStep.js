import {Avatar, Checkbox, Chip, Divider, FormControlLabel} from "@mui/material";
import {useTranslation} from "react-i18next";
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import CompactMediaCard from "../../media/CompactMediaCard";
import CloseIcon from '@mui/icons-material/Close';

const LastStep = (list) => {
    const {t} = useTranslation();

    return (
        <div className="px-5 pt-3 my-3 space-y-2 text-semibold w-full">
            <div className="py-1">
                <CheckCircleOutlineIcon className="text-violet-500 mb-1 mr-1"/>
                Almost Ready!
                Please, verify your new list data before proceeding!
            </div>
            <Divider className="text-violet-500"/>
            <div className="flex flex-wrap pt-1">
                <h2 className="text-xl fw-bolder">
                    {list.name}
                </h2>
                {/*    report and favorite buttons */}
            </div>
            {/*    list author and forking info*/}
            {/*    forked from*/}
            {/*amount of forks*/}
            <p className="font-thin text-base text-justify max-w-full break-words">
                {list.description}
            </p>
            <div className="flex justify-between pt-1 px-2">
                <FormControlLabel control={<Checkbox checked={list.isPublic} color="secondary"/>} label={"Is Public"}/>
                <FormControlLabel control={<Checkbox checked={list.isCollaborative} color="secondary"/>}
                                  label={"Is Collaborative"}/>
            </div>
            {list.collaborators.length > 0 ? <div>
                <div className="flex flex-col py-1">
                    {t('list_already_collab')}
                </div>
                {list.collaborators.map(user => {
                    return <Chip
                        label={user.username}
                        variant="outlined" color="secondary"
                        avatar={<Avatar alt={user.username} src={user.imageUrl}/>}
                        key={user.username}
                    />
                })}
            </div> : <>{list.isCollaborative &&
                <div className="font-thin text-base text-justify max-w-full break-words"><CloseIcon
                    className="text-violet-500 mb-1"/>No collaborators in this list</div>}</>}
            {list.media.length > 0 && <div>
                <div className="flex flex-col py-1">
                    {t('list_already_media')}
                </div>
                {list.media.map(media => {
                    return <CompactMediaCard canDelete={false} title={media.title}
                                             releaseDate={media.releaseDate.slice(0, 4)}
                                             image={media.imageUrl}
                                             className="mb-1"
                                             key={media.id}/>
                })}
            </div>}
            {list.media.length === 0 &&
                <div className="font-thin text-base text-justify max-w-full break-words"><CloseIcon
                    className="text-violet-500 mb-1"/>No media in this list</div>}
        </div>);
}
export default LastStep;