import {act, fireEvent, render, screen} from '@testing-library/react';
import {setupTests} from "./testUtils/setupTests";
import renderFromRoute from "./testUtils/renderFromRoute";
import userEvent from "@testing-library/user-event";


setupTests()

test('login', async () => {
    await act(async () => renderFromRoute('/login'));

    fireEvent.change(screen.getByTestId('username-input'), {
        target: {value: 'PopCult'},
    });
    fireEvent.change(screen.getByTestId('password-input'), {
        target: {value: '123123123'},
    });
    userEvent.click(await screen.getByTestId('login-button'));

    setTimeout(() => {
        expect(sessionStorage.getItem("userAuthToken")).not.toBeNull();
    }, 0)
});

