import {FormControl, InputLabel, Select} from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import {MediaType} from "../../../enums/MediaType";
import {useTranslation} from "react-i18next";
import CommonFilters from "./CommonFilters";

const MediaFilters = (props) => {
    const {t} = useTranslation();

    const style = {
        "& .Mui-focused .MuiOutlinedInput-notchedOutline": {
            borderColor: 'rgb(139 92 246)',
        }, m: 1, minWidth: 120, borderColor: 'rgb(139 92 246)'
    }
    const handleType = (event) => {
        props.setType(event.target.value);
    };

    return <div className="flex justify-items-start flex-wrap">
        <CommonFilters sortBy={props.sortBy} setSortBy={props.setSortBy} categories={props.categories}
                       setCategories={props.setCategories}
                       decades={props.decades} setDecades={props.setDecades} genres={props.genres}>
            {/* Types */}
            {props.showMediaType && <FormControl sx={style} size="small">
                <InputLabel id="types-select-label" className="text-violet-500">{t('search_types')}</InputLabel>
                <Select
                    labelId="types-select-label"
                    id="types-select"
                    value={props.type}
                    label={t('search_types')}
                    onChange={handleType}
                >
                    <MenuItem value={MediaType.FILMS}>{t('nav_films')}</MenuItem>
                    <MenuItem value={MediaType.SERIES}>{t('nav_series')}</MenuItem>
                    <MenuItem value={''}>{t('search_all')}</MenuItem>
                </Select>
            </FormControl>}
        </CommonFilters>

    </div>;
}
export default MediaFilters;