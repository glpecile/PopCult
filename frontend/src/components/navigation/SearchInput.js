import SearchIcon from '@mui/icons-material/Search';
import {useTranslation} from "react-i18next";
import {useState} from "react";
import {createSearchParams, useNavigate} from "react-router-dom";

const SearchInput = () => {
    const {t} = useTranslation();
    const [searchTerm, setSearchTerm] = useState('');
    const navigate = useNavigate();

    const setTerm = (event) => {
        event.target.validity.valid ? setSearchTerm(event.target.value) : setSearchTerm(searchTerm);
    }

    const submitHandler = (event) => {
        event.preventDefault();
        navigate({
            pathname: '/search',
            search: createSearchParams({
                term: searchTerm,
                mp: 1,
                lp: 1
            }).toString()
        });
    }
    return (
        <form className="m-0 p-0" encType="application/x-www-form-urlencoded" onSubmit={submitHandler}>
            <div className="relative">
                <label className="p-2 text-semibold w-full flex">
                    <input className="form-control text-base rounded-full h-8 shadow-sm pl-3 pr-8" type="text"
                           name="term" placeholder={t('search_placeholder')} pattern="[^/><]+" value={searchTerm}
                           onChange={setTerm}/>
                    <button
                        className="btn btn-link bg-transparent rounded-full h-8 w-8 p-2 absolute inset-y-3 right-2 flex items-center"
                        type="submit">
                        <SearchIcon
                            className="text-gray-500 hover:text-gray-800 text-center rounded-full mb-2 pr-2"></SearchIcon>
                    </button>
                </label>
            </div>
        </form>
    );
}
export default SearchInput;