import {useState} from "react";

function LoginForm(props) {
    const [enteredUsername, setEnteredUsername] = useState('');
    const [enteredPassword, setEnteredPassword] = useState('');
    const [enteredRememberMe, setEnteredRememberMe] = useState(false);
    const UsernameChangeHandler = (event) => {
        setEnteredUsername(event.target.value);
    };
    const PasswordChangeHandler = (event) => {
        setEnteredPassword(event.target.value);
    };
    const RememberMeChangeHandler = (event) => {
        setEnteredRememberMe(event.target.value);
    };

    const submitHandler = (event) => {
        event.preventDefault();
        console.log(enteredUsername.length >= 4);
        console.log(enteredUsername.match("[a-zA-Z0-9]+") && enteredUsername.length >= 4)
        if (enteredUsername.match('[a-zA-Z0-9]+') && enteredUsername.length >= 4 && enteredPassword.length >= 8) {
            props.onSuccessfullLogIn();
        }
    };

    return (
        <form className="space-y-4" onSubmit={submitHandler}>
            <div className="flex flex-col justify-center items-center">
                <label className="py-2 text-semibold w-full"/> Username:
                <input className="form-control shadow-sm" type="text" value={enteredUsername}
                       pattern="[a-zA-Z0-9]+" minLength={4} maxLength={100}
                       onChange={UsernameChangeHandler}/>
                <label className="py-2 text-semibold w-full"/>Password:
                <input className="form-control shadow-sm" type="password" value={enteredPassword}
                       minLength={8} maxLength={100}
                       onChange={PasswordChangeHandler}/>
                <label className="py-2 text-semibold w-full"/>Remember me
                <input className="shadow-sm" type="checkbox" defaultChecked={enteredRememberMe}
                       onChange={RememberMeChangeHandler}/>
                <button className="btn btn-secondary my-2 w-full" type="submit">
                    Login
                </button>
            </div>
        </form>
    );
}

export default LoginForm;