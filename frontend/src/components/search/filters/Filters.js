import {useEffect, useState} from "react";
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';
import CancelOutlinedIcon from '@mui/icons-material/CancelOutlined';
import MediaFilters from "./MediaFilters";
import ListFilters from "./ListFilters";
import {Divider} from "@mui/material";
import {useTranslation} from "react-i18next";

const Filters = (props) => {
    const {t} = useTranslation();

    const [type, setType] = useState('');
    const [mSortBy, setMSortBy] = useState('');
    const [mDecades, setMDecades] = useState(  '');
    const [mCategories, setMCategories] = useState( []);
    const [lSortBy, setLSortBy] = useState(  '');
    const [lDecades, setLDecades] = useState( '');
    const [lCategories, setLCategories] = useState( []);

    useEffect(() => {
        if (props.mediaFilters && props.mediaFilters.has(props.mediaType)) setType(props.mediaFilters.get(props.mediaType));
        if (props.mediaFilters && props.mediaFilters.has(props.mediaSort)) setMSortBy(props.mediaFilters.get(props.mediaSort));
        if (props.mediaFilters && props.mediaFilters.has(props.mediaDecades)) setMDecades(props.mediaFilters.get(props.mediaDecades));
        if (props.mediaFilters && props.mediaFilters.has(props.mediaCategories))setMCategories(props.mediaFilters.get(props.mediaCategories));
        if (props.listFilters && props.listFilters.has(props.listSort)) setLSortBy(props.listFilters.get(props.listSort));
        if (props.listFilters && props.listFilters.has(props.listDecades)) setLDecades(props.listFilters.get(props.listDecades));
        if (props.listFilters && props.listFilters.has(props.listCategories)) setLCategories(props.listFilters.get(props.listCategories));
    }, [props]);

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