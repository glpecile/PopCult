import {useState} from "react";
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';
import CancelOutlinedIcon from '@mui/icons-material/CancelOutlined';
import MediaFilters from "./MediaFilters";
import ListFilters from "./ListFilters";
import {Divider} from "@mui/material";
import {useTranslation} from "react-i18next";

const Filters = (props) => {
    const {t} = useTranslation();

    const mediaSort = 'msort';
    const mediaType = 'mtype';
    const mediaDecades = 'mdecades';
    const mediaCategories = 'mcategories';
    const [type, setType] = useState(props.mediaFilters.has(mediaType) ? props.mediaFilters.get(mediaType) : '');
    const [mSortBy, setMSortBy] = useState(props.mediaFilters.has(mediaSort) ? props.mediaFilters.get(mediaSort) : '');
    const [mDecades, setMDecades] = useState(props.mediaFilters.has(mediaDecades) ? props.mediaFilters.get(mediaDecades) : '');
    const [mCategories, setMCategories] = useState(props.mediaFilters.has(mediaCategories) ? props.mediaFilters.get(mediaCategories) : []);
    const listSort = 'lsort';
    const listDecades = 'ldecades';
    const listCategories = 'lcategories';
    const [lSortBy, setLSortBy] = useState(props.listFilters.has(listSort) ? props.listFilters.get(listSort) : '');
    const [lDecades, setLDecades] = useState(props.listFilters.has(listDecades) ? props.listFilters.get(listDecades) : '');
    const [lCategories, setLCategories] = useState(props.listFilters.has(listCategories) ? props.listFilters.get(listCategories) : []);


    const handleMediaFilters = (key, value) => {
        props.setMediaPage(1);
        if (value !== '') {
            props.setMediaFilters(prev => new Map([...prev, [key, value]]));
        } else if (props.mediaFilters.has(key)) {
            const aux = props.mediaFilters;
            aux.delete(key);
            props.setMediaFilters(aux);
        }
    }

    const handleListsFilters = (key, value) => {
        props.setListPage(1);
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
        handleMediaFilters(mediaSort, mSortBy);
        handleMediaFilters(mediaType, type);
        handleMediaFilters(mediaDecades, mDecades);
        handleMediaFilters(mediaCategories, mCategories);
        handleListsFilters(listSort, lSortBy);
        handleListsFilters(listDecades, lDecades);
        handleListsFilters(listCategories, lCategories);
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
        <Divider className="text-violet-500"/> {/* despues esto se puede sacar */}
        <form onSubmit={submitHandler} className="flex justify-between py-2">
            {props.showMediaFilters ?
                (<MediaFilters sortBy={mSortBy} setSortBy={setMSortBy} categories={mCategories}
                               setCategories={setMCategories}
                               decades={mDecades} setDecades={setMDecades} type={type} setType={setType} genres={props.genres}/>)
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
        <Divider className="text-violet-500"/>
    </>;
}

export default Filters;