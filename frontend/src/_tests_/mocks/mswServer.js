import { setupServer } from 'msw/node';
import { userHandlers, listHandlers } from './handlers/handlers';

export const mswServer = setupServer(...userHandlers, ...listHandlers);