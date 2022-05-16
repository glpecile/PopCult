import ListsCard from "../lists/ListsCard";

const ListsInMedia = (props) => {
    return (
        <div className="my-4">
            <h5 className="font-bold text-2xl py-2">
                {props.title}
            </h5>
            <div className="grid lg:grid-cols-4 md:grid-cols-2 gap-12 md:gap-4">
                {
                    props.data.map(
                        content => {
                            return (<ListsCard id={content.id} key={content.id}
                                               mediaUrl={content.mediaUrl}
                                               listTitle={content.name}/>)
                        }
                    )
                }
            </div>
        </div>
    )
}

export default ListsInMedia;