import {Alert, Snackbar} from "@mui/material";

const ErrorSnackbar = (props) => {
    return (
        <Snackbar open={props.show} autoHideDuration={6000}
                  anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
            <Alert severity={"error"}>
                {props.text}
            </Alert>
        </Snackbar>
    )
}

export default ErrorSnackbar;