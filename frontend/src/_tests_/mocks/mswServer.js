import { setupServer } from 'msw/node';
import {userHandlers, listHandlers, mediaHandlers} from './handlers/handlers';

export const mswServer = setupServer(...userHandlers, ...listHandlers, ...mediaHandlers);