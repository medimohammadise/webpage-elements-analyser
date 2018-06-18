import { Routes } from '@angular/router';
import { configurationRoute, docsRoute, logsRoute } from './';
const ADMIN_ROUTES = [configurationRoute, docsRoute, logsRoute];
export const adminState: Routes = [
    {
        path: '',
        data: {
            authorities: ['ROLE_ADMIN']
        },
        canActivate: [],
        children: ADMIN_ROUTES
    }
];
