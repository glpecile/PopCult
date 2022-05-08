import CommonFilters from "./CommonFilters";

const ListFilters = (props) => {
    return <CommonFilters sortBy={props.sortBy} setSortBy={props.setSortBy} categories={props.categories}
                          setCategories={props.setCategories}
                          decades={props.decades} setDecades={props.setDecades} genres={props.genres}/>
}
export default ListFilters;