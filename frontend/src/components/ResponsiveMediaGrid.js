const ResponsiveMediaGrid = (props) => {
    return (<div className="grid lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 gap-4 mb-3">
        {props.children}
    </div>)
}

export default ResponsiveMediaGrid