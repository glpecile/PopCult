import {ReactComponent as Ellipsis} from './Ellipsis.svg'

const Spinner = () => {
    return (
        <div className="flex justify-center">
            <Ellipsis style={{background: 'transparent'}}/>
        </div>
    );
}

export default Spinner;