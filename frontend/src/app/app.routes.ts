import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { Dashboard } from './features/dashboard/dashboard';
import { authGuard } from './core/guards/auth-guard';
import { MainLayout } from './layout/main-layout/main-layout';
import { Lancamentos } from './features/lancamentos/lancamentos';
import { FormLancamento } from './features/lancamentos/form-lancamento/form-lancamento';

export const routes: Routes = [
    {
        path: 'login',
        component: Login
    },
    {
        path: '',
        component: MainLayout,
        canActivate: [authGuard],
        children: [
            {
                path: 'dashboard',
                component: Dashboard
            },
            {
                path: 'lancamentos',
                component: Lancamentos
            },
            {
                path: '',
                redirectTo: 'dashboard',
                pathMatch: 'full'
            }, 
            {
                path: 'lancamentos', 
                component: Lancamentos
            }, 
            {
                path: 'lancamentos/novo', 
                component: FormLancamento
            }, 
            {
                path: 'lancamentos/:id/editar', 
                component: FormLancamento
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'dashboard'
    }
];
