import {rest} from 'msw'
import {media, mediaList} from "../modelMocks";

export const mediaHandler = [

    rest.get('/media', (req, res, ctx) => {
        if (req.headers.get('Authorization') === 'Basic UG9wQ3VsdDoxMjMxMjMxMjM=') {
            return res(
                ctx.status(200),
                ctx.set('Authorization', `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQb3BDdWx0IiwiYXV0aG9yaXphdGlvbiI6IkFETUlOIiwiaWF0IjoxNjUyNjE4NDMyLCJleHAiOjE2NTMyMjMyMzJ9.ho8tu14aafj6LvXyRb7ST7bYlcqNj2usHjN2KI2FeQdApqTDuvH4kcc_sR12vwT45lOhGP-L9Mfj8niS14vwdw`)
            )
        }
        return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body(mediaList)
        )
    }),

    rest.get('/media/1', (req, res, ctx) => {
        return res(
            // Respond with a 200 status code
            ctx.status(200),
            ctx.body(media)
        )
    })
]

export default mediaHandler