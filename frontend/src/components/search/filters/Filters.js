import {useState} from "react";
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';
import CancelOutlinedIcon from '@mui/icons-material/CancelOutlined';
import MediaFilters from "./MediaFilters";
import ListFilters from "./ListFilters";
import {Divider} from "@mui/material";
import {useTranslation} from "react-i18next";

const Filters = (props) => {
    const {t} = useTranslation();

    const [type, setType] = useState((props.mediaFilters && props.mediaFilters.has(props.mediaType)) ? props.mediaFilters.get(props.mediaType) : '');
    const [mSortBy, setMSortBy] = useState((props.mediaFilters && props.mediaFilters.has(props.mediaSort)) ? props.mediaFilters.get(props.mediaSort) : '');
    const [mDecades, setMDecades] = useState((props.mediaFilters && props.mediaFilters.has(props.mediaDecades)) ? props.mediaFilters.get(props.mediaDecades) : '');
    const [mCategories, setMCategories] = useState((props.mediaFilters && props.mediaFilters.has(props.mediaCategories)) ? props.mediaFilters.get(props.mediaCategories) : []);
    const [lSortBy, setLSortBy] = useState((props.listFilters && props.listFilters.has(props.listSort)) ? props.listFilters.get(props.listSort) : '');
    const [lDecades, setLDecades] = useState((props.listFilters && props.listFilters.has(props.listDecades)) ? props.listFilters.get(props.listDecades) : '');
    const [lCategories, setLCategories] = useState((props.listFilters && props.listFilters.has(props.listCategories)) ? props.listFilters.get(props.listCategories) : []);


    const handleMediaFilters = (key, value) => {
        if (value !== '') {
            props.setMediaFilters(prev => new Map([...prev, [key, value]]));
        } else if (props.mediaFilters.has(key)) {
            const aux = props.mediaFilters;
            aux.delete(key);
            props.setMediaFilters(aux);
        }
    }

    const handleListsFilters = (key, value) => {
        if (value !== '') {
            props.setListFilters(prev => new Map([...prev, [key, value]]));
        } else if (props.listFilters.has(key)) {
            const aux = props.listFilters;
            aux.delete(key);
            props.setListFilters(aux);
        }
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if (props.showMediaFilters) {
            props.setMediaPage(1);
            handleMediaFilters(props.mediaSort, mSortBy);
            handleMediaFilters(props.mediaType, type);
            handleMediaFilters(props.mediaDecades, mDecades);
            handleMediaFilters(props.mediaCategories, mCategories);
        } else {
            props.setListPage(1);
            handleListsFilters(props.listSort, lSortBy);
            handleListsFilters(props.listDecades, lDecades);
            handleListsFilters(props.listCategories, lCategories);
        }
    }

    const cleanFilters = () => {
        if (props.showMediaFilters) {
            setMSortBy('');
            setType('');
            setMDecades('');
            setMCategories([]);
        } else {
            setLSortBy('');
            setLDecades('');
            setLCategories([]);
        }
    }

    return <>
        <Divider className="text-violet-500"/>
        <form onSubmit={submitHandler} className="flex justify-between flex-wrap mb-1.5 py-2">
            {props.showMediaFilters ?
                (<MediaFilters sortBy={mSortBy} setSortBy={setMSortBy} categories={mCategories}
                               setCategories={setMCategories}
                               decades={mDecades} setDecades={setMDecades} type={type} setType={setType}
                               genres={props.genres} showMediaType={props.showMediaType}/>)
                :
                (<ListFilters sortBy={lSortBy} setSortBy={setLSortBy} categories={lCategories}
                              setCategories={setLCategories}
                              decades={lDecades} setDecades={setLDecades} genres={props.genres}/>)
            }
            <div className="flex justify-end">
                <button type="submit" className="btn btn-link my-2 text-violet-500 hover:text-violet-900 btn-rounded">
                    <FilterAltOutlinedIcon className="pb-1"/>{t('search_filters_apply')}
                </button>
                <button className="btn btn-link my-2 text-violet-500 hover:text-violet-900 btn-rounded"
                        onClick={cleanFilters}><CancelOutlinedIcon className="pb-1"/>{t('search_filters_clear')}
                </button>
            </div>
        </form>
    </>;
}

export default Filters;