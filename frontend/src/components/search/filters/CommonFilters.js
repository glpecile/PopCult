import {Checkbox, Chip, FormControl, InputLabel, ListItemText, OutlinedInput, Select} from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import Box from "@mui/material/Box";
import {Decades} from "../../../enums/Decades";
import {useTranslation} from "react-i18next";

const CommonFilters = (props) => {
    const decades = Object.keys(Decades);
    const {t} = useTranslation();

    const style = {
        "& .Mui-focused .MuiOutlinedInput-notchedOutline": {
            borderColor: 'rgb(139 92 246)',
        }, m: 1, minWidth: 120, borderColor: 'rgb(139 92 246)'
    }

    const handleSortBy = (event) => {
        props.setSortBy(event.target.value);
    };

    const handleDecades = (event) => {
        props.setDecades(event.target.value);
    };

    const handleGenres = (event) => {
        const {
            target: {value},
        } = event;
        props.setCategories(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    return <div className="flex justify-items-start">
        {/* Sorted By */}
        {props.genres && <><FormControl sx={style} size="small">
            <InputLabel id="sort-by-select-label" className="text-violet-500">{t('search_sort')}</InputLabel>
            <Select
                labelId="sort-by-select-label"
                id="sort-by-select"
                value={props.sortBy}
                label={t('search_sort')}
                onChange={handleSortBy}
            >
                <MenuItem value={"DATE"}>{t('search_sort_date')}</MenuItem>
                <MenuItem value={"TITLE"}>{t('search_sort_title')}</MenuItem>
            </Select>
        </FormControl>
            {props.children}
            {/*Decades*/}
            <FormControl sx={style} size="small">
                <InputLabel id="decades-select-label" className="text-violet-500">{t('search_decades')}</InputLabel>
                <Select
                    labelId="decades-select-label"
                    id="decades-select"
                    value={props.decades}
                    label={t('search_decades')}
                    onChange={handleDecades}
                >
                    {decades.map((key) => {
                        return <MenuItem key={key} value={key}>{key}</MenuItem>
                    })}
                    <MenuItem value={''}>{t('search_all')}</MenuItem>
                </Select>
            </FormControl>

            {/*Categories*/}

            <FormControl sx={style} size="small">
                <InputLabel id="categories-select-label"
                            className="text-violet-500">{t('search_categories')}</InputLabel>
                <Select
                    labelId="categories-select-label"
                    id="categories-select"
                    label={t('search_categories')}
                    multiple
                    value={props.categories}
                    onChange={handleGenres}
                    input={<OutlinedInput id="select-multiple-chip" label="Chip"/>}
                    renderValue={(selected) => (
                        <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                            {selected.map((value) => (
                                <Chip color="secondary" key={value}
                                      label={value} variant="outlined"/>
                            ))}
                        </Box>
                    )}
                >
                    {props.genres.map((genre) => {
                        return (<MenuItem key={genre.genre} value={genre.genre}>
                            <Checkbox checked={props.categories.includes(genre.genre)}/>
                            <ListItemText primary={t('genre_' + genre.genre.toLowerCase())}/>
                        </MenuItem>);
                    })}
                </Select>
            </FormControl></>}

    </div>;
}
export default CommonFilters;