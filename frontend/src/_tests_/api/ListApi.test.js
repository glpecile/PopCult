import {setupTests} from "../testUtils/setupTests";
import listApi from "../../api/ListApi";


setupTests()

test('get lists', async () => {
    const res = await listApi.getLists({page: 1, pageSize: 2})

    expect(res.status).toBe(200)
    expect(res.data).toHaveLength(2)
});

test('get list by id', async () => {
    const res = await listApi.getListById(1)

    expect(res.status).toBe(200)
    expect(res.data.id).toBe(1)
});