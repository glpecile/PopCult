import { setupServer } from 'msw/node';
import { userHandlers } from './handlers/handlers';

export const mswServer = setupServer(...userHandlers);