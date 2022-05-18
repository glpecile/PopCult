import {setupTests} from "../testUtils/setupTests";
import mediaApi from "../../api/MediaApi.js";

setupTests()

test('get media', async () => {
    const res = await mediaApi.getMediaList({page: 1, pageSize: 2})

    expect(res.status).toBe(200)
    expect(res.data).toHaveLength(2)
});

test('get media by id', async () => {
    const res = await mediaApi.getMedia(1)

    expect(res.status).toBe(200)
    expect(res.data.id).toBe(1)
});