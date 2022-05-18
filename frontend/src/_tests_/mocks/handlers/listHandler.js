import {rest} from 'msw'
import {listList, list} from "../modelMocks";

export const listHandler = [
    rest.get("/lists", (req, res, ctx) => {
        return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body(listList)
        )
    }),

    rest.get("/lists/1", (req, res, ctx) => {
        return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body(list)
        )
    })
]

export default listHandler