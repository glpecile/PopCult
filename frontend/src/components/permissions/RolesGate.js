import {useContext} from "react";
import AuthContext from "../../store/AuthContext";
import {Roles} from "../../enums/Roles";
import Error401 from "../../pages/secondary/errors/Error401";

const RolesGate = ({children, level}) => {
    const user = useContext(AuthContext);
    const role = user.role;

    switch (level) {
        case Roles.ADMIN:
            if (!user.isLoggedIn || role.localeCompare(Roles.ADMIN) !== 0){
                return <Error401/>
            }
            break;
        case Roles.MOD:
            if (!user.isLoggedIn || !(role.localeCompare(Roles.ADMIN) === 0 || role.localeCompare(Roles.MOD) === 0)){
                return <Error401/>
            }
            break;
        default:
            break;
    }

    return <>{children}</>
}
export default RolesGate;