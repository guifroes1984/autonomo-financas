import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { Dashboard } from './features/dashboard/dashboard';
import { authGuard } from './core/guards/auth-guard';
import { MainLayout } from './layout/main-layout/main-layout';
import { Lancamentos } from './features/lancamentos/lancamentos';
import { FormLancamento } from './features/lancamentos/form-lancamento/form-lancamento';
import { Categorias } from './features/categorias/categorias';
import { FormCategoria } from './features/categorias/form-categoria/form-categoria';
import { Plataformas } from './features/plataformas/plataformas';
import { FormPlataforma } from './features/plataformas/form-plataforma/form-plataforma';
import { Metas } from './features/metas/metas';
import { Relatorios } from './features/relatorios/relatorios';
import { Perfil } from './features/perfil/perfil';

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
            },
            {
                path: 'categorias',
                component: Categorias
            },
            {
                path: 'categorias/novo',
                component: FormCategoria
            },
            {
                path: 'categorias/:id/editar',
                component: FormCategoria
            },
            {
                path: 'plataformas',
                component: Plataformas
            },
            {
                path: 'plataformas/novo',
                component: FormPlataforma
            },
            {
                path: 'plataformas/:id/editar',
                component: FormPlataforma
            },
            {
                path: 'metas',
                component: Metas
            },
            {
                path: 'relatorios',
                component: Relatorios
            }, 
            {
                path: 'perfil',
                component: Perfil
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'dashboard'
    }
];
