import {useState} from "react";

const RegisterForm = (props) => {
    const [enteredName, setEnteredName] = useState('');
    const [enteredNameError, setNameError] = useState(false);

    const NameChangeHandler = (event) => {
        setEnteredName(event.target.value);
        event.target.validity.valid ? setNameError(false) : setNameError(true);
    };

    const submitHandler = (event) => {
        event.preventDefault();
    }
    return (
        <form className="m-0 p-0" onSubmit={submitHandler}>
            <div className="flex flex-col justify-center items-center">
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Email*</label>
                    <input type="text" className="form-control"/>
                </div>
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Password*</label>
                    <input type="password" className="form-control"/>
                    <small id="passwordHelpBlock" className="form-text text-muted">
                        Your password must be 8-20 characters long, contain letters and numbers, and must not contain
                        spaces, special characters, or emoji.
                    </small>


                </div>
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Repeat Password*</label>
                    <input type="password" className="form-control"/>


                </div>
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Username*</label>
                    <input type="text" className="form-control"/>

                </div>
                <div className=" relative py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Name*</label>
                    {enteredNameError && <span className="absolute inset-y-0 top-2 right-5 flex items-center pl-2 text-rose-500"><i className="fas fa-exclamation-circle"/></span>}
                    <input type="text"
                           className={"w-full rounded " + (enteredNameError ? "border-2 border-rose-500" : "")}
                           defaultValue={enteredName} onChange={NameChangeHandler}
                           minLength={3} maxLength={100} pattern="[a-zA-Z0-9\\s]+"/>
                    {enteredNameError &&
                        <p className="text-red-500 text-xs italic">Valid names contain between 3 and 20
                            characters.</p>
                    }
                </div>

                <div className="py-1 px-2.5 text-semibold w-full">
                    <button className="btn btn-secondary px-2.5 mt-2 w-full" type="submit">
                        Sign Up
                    </button>
                </div>
            </div>
        </form>);
}
export default RegisterForm;