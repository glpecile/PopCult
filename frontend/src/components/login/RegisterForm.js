const RegisterForm = () => {
    const submitHandler = () => {

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


                </div>
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Repeat Password*</label>
                    <input type="password" className="form-control"/>


                </div>
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Username*</label>
                    <input type="text" className="form-control" />

                </div>
                <div className="py-1 px-2.5 text-semibold w-full">
                    <label className="py-2 text-semibold w-full">Name*</label>
                    <input type="text" className="form-control"/>

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