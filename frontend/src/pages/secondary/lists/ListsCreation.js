import NewListStepper from "../../../components/lists/creation/NewListStepper";
import LoggedGate from "../../../components/permissions/LoggedGate";

const ListsCreation = () => {

    return (<LoggedGate>
            <div className="flex-grow col-8 offset-2">
                <div className="row g-3 p-2 my-8 bg-white shadow-lg rounded-lg">
                    <NewListStepper/>
                </div>
            </div>
        </LoggedGate>
    );
}

export default ListsCreation;