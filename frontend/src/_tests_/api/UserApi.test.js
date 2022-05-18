import {setupTests} from "../testUtils/setupTests";
import userApi from "../../api/UserApi";

setupTests()

test('get users', async () => {
    const res = await userApi.getUsers({page: 1, pageSize: 2})

    expect(res.status).toBe(200)
    expect(res.data).toHaveLength(2)
});

test('get user with username', async () => {
    const res = await userApi.getUserByUsername("john")

    expect(res.status).toBe(200)
    expect(res.data.username).toBe("john")
});