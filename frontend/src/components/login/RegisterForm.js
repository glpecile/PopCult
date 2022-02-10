import {useState} from "react";

const RegisterForm = (props) => {
    const [enteredName, setEnteredName] = useState('');
    const [enteredNameError, setNameError] = useState(false);
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredUsernameError, setUsernameError] = useState(false);
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredPasswordError, setPasswordError] = useState(false);
    const [enteredRepeatedPassword, setEnteredRepeatedPassword] = useState('');
    const [enteredRepeatedPasswordError, setRepeatedPasswordError] = useState(false);
    const [enteredEmail, setEnteredEmail] = useState('');
    const [enteredEmailError, setEmailError] = useState(false);
    const [alertDisplay, setAlertDisplay] = useState(false);

    const NameChangeHandler = (event) => {
        setEnteredName(event.target.value);
        event.target.validity.valid ? setNameError(false) : setNameError(true);
    };

    const UsernameChangeHandler = (event) => {
        setEnteredUsername(event.target.value);
        event.target.validity.valid ? setUsernameError(false) : setUsernameError(true);
    };

    const PasswordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
        event.target.validity.valid ? setPasswordError(false) : setPasswordError(true);
        if (enteredRepeatedPassword.localeCompare('') !== 0) {
            (event.target.value.localeCompare(enteredRepeatedPassword) === 0) ? setRepeatedPasswordError(false) : setRepeatedPasswordError(true);
        }
    };
    const RepeatedPasswordChangeHandler = (event) => {
        setEnteredRepeatedPassword(event.target.value);
        (enteredPassword.localeCompare(event.target.value) === 0) ? setRepeatedPasswordError(false) : setRepeatedPasswordError(true);
    };
    const EmailChangeHandler = (event) => {
        setEnteredEmail(event.target.value);
        event.target.validity.valid ? setEmailError(false) : setEmailError(true);
    };

    const displayAlert = () => {
        setAlertDisplay(true);
        setTimeout(() => {
            setAlertDisplay(false);
        }, 5000);
    }
    const formHasErrors = () => {
        const hasErrors = !(enteredEmailError || enteredUsernameError || enteredNameError || enteredPasswordError || enteredRepeatedPasswordError);
        const isEmpty = !(enteredEmail.length === 0 || enteredUsername.length === 0 || enteredName.length === 0 || enteredPassword.length === 0 || enteredRepeatedPassword.length === 0);
        return hasErrors && isEmpty;
    }

    const submitHandler = (event) => {
        event.preventDefault();
        if (formHasErrors()) {
            props.onSuccessfulRegister();
        }
    }

    return (
        <form className="m-0 p-0" onSubmit={submitHandler}>
            {/*Email*/}
            <div className="flex flex-col justify-center items-center">
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        {enteredEmailError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <label className="py-2 text-semibold w-full">Email*</label>
                        <input type="text"
                               className={"w-full rounded active:none " + (enteredEmailError ? "border-2 border-rose-500" : "")}
                               pattern="[^@]+@[^@]+\.[^@]+" minLength={6} maxLength={100} defaultValue={enteredEmail}
                               onChange={EmailChangeHandler}/>
                    </div>
                    {enteredEmailError &&
                        <p className="text-red-500 text-xs italic">Please insert a valid email.<br/> For example:
                            something@example.com</p>}
                </div>

                {/*Pass*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        {enteredPasswordError ?
                            (<span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"
                                   onClick={displayAlert}>
                                <i className="fas fa-exclamation-circle"/>
                            </span>) :
                            (<span className="absolute inset-y-0 -top-10 right-3 flex items-center pl-2"
                                   onClick={displayAlert}>
                                    <i className="fas fa-question-circle"/>
                            </span>)}
                        <label className="py-2 text-semibold w-full">Password*</label>
                        <input type="password"
                               className={"w-full rounded active:none " + (enteredPasswordError ? "border-2 border-rose-500" : "")}
                               minLength={8} maxLength={100}
                               defaultValue={enteredPassword} onChange={PasswordChangeHandler}/>
                    </div>
                    {enteredPasswordError &&
                        <p className="text-red-500 text-xs italic">Please insert a valid password.</p>}

                </div>
                {/*TODO ayuda con el fading por favor*/}
                {alertDisplay &&
                    <div className="py-1 px-2.5 collapse show z-50 fixed absolute" id="alert">
                        <div className="alert bg-gray-100/90 text-gray-900 d-flex align-items-center shadow-lg"
                             role="alert">
                            <span className="absolute top-0 bottom-0 right-0 px-4 py-3">
                                <button onClick={() => setAlertDisplay(false)}><i className="fas fa-times"/></button>
                            </span>
                            <small id="passwordHelpBlock" className="form-text text-muted">
                                Your password must be 8-20 characters long,<br/>
                                contain letters and numbers, and must not contain<br/>
                                spaces, special characters, or emoji.<br/>
                            </small>
                        </div>
                    </div>}

                {/*repPass*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        <label className="py-2 text-semibold w-full">Repeat Password*</label>
                        {enteredRepeatedPasswordError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <input type="password"
                               className={"w-full rounded active:none " + (enteredRepeatedPasswordError ? "border-2 border-rose-500" : "")}
                               minLength={8} maxLength={100} onChange={RepeatedPasswordChangeHandler}/>
                    </div>
                    {enteredRepeatedPasswordError &&
                        <p className="text-red-500 text-xs italic">Passwords don't match.</p>}
                </div>
                {/*Username*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        <label className="py-2 text-semibold w-full">Username*</label>
                        {enteredUsernameError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <input type="text"
                               className={"w-full rounded active:none " + (enteredUsernameError ? "border-2 border-rose-500" : "")}
                               pattern="[a-zA-Z0-9]+" minLength={1}
                               maxLength={100} defaultValue={enteredUsername} onChange={UsernameChangeHandler}/>
                    </div>
                    {enteredUsernameError &&
                        <p className="text-red-500 text-xs italic">Valid usernames contain between 1 and 20
                            non special characters.</p>
                    }
                </div>

                {/*Name*/}
                <div className="py-1 px-2.5 text-semibold w-full">
                    <div className="relative">
                        <label className="py-2 text-semibold w-full">Name*</label>
                        {enteredNameError &&
                            <span className="absolute inset-y-0 top-10 right-3 flex items-center pl-2 text-rose-500"><i
                                className="fas fa-exclamation-circle"/></span>}
                        <input type="text"
                               className={"w-full rounded active:none " + (enteredNameError ? "border-2 border-rose-500" : "")}
                               defaultValue={enteredName} onChange={NameChangeHandler}
                               minLength={3} maxLength={100} pattern="[a-zA-Z0-9\\s]+"/>
                    </div>
                    {enteredNameError &&
                        <p className="text-red-500 text-xs italic">Valid names contain between 3 and 20
                            non special characters.</p>
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