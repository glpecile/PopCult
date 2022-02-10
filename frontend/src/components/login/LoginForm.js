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
        if (enteredUsername.match('[a-zA-Z0-9]+') && enteredUsername.length >= 4 && enteredPassword.length >= 8) {
            props.onSuccessfullLogIn();
        } else {
            const elem = document.getElementById('alert');
            console.log(elem);
            elem.style.display='block';
            setTimeout(() => {
                elem.style.display='none';
            }, 3000);
        }
    };

    return (<>
            <form className="my-1.5" onSubmit={submitHandler}>
                <div className="flex flex-col">
                    <label className="py-2 text-semibold w-full">Username:</label>
                    <input className="form-control shadow-sm accent-purple-400" type="text" value={enteredUsername}
                           pattern="[a-zA-Z0-9]+" minLength={4} maxLength={100}
                           onChange={UsernameChangeHandler}/>
                    <label className="py-2 text-semibold w-full">Password:</label>
                    <input className="form-control shadow-sm" type="password" value={enteredPassword}
                           minLength={8} maxLength={100}
                           onChange={PasswordChangeHandler}/>
                    <div className="flex pt-2 justify-start">
                        <input className="shadow-sm mt-1.5 accent-purple-400" type="checkbox"
                               defaultChecked={enteredRememberMe}
                               onChange={RememberMeChangeHandler}/>
                        <label className="text-semibold pl-1.5">Remember me</label>
                    </div>

                    <button className="btn btn-secondary my-2 w-full" type="submit">
                        Log In
                    </button>
                </div>
            </form>
            <div className="collapse fixed bottom-1 z-50" id="alert">
                <div className="alert alert-danger d-flex align-items-center shadow-lg" role="alert">
                    <i className="fas fa-exclamation mr-2"/>Your username or password is wrong.
                </div>
            </div>
        </>
    );
}

export default LoginForm;