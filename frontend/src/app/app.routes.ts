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
import { Cadastro } from './features/auth/cadastro/cadastro';
import { EsqueciSenha } from './features/auth/esqueci-senha/esqueci-senha';
import { RedefinirSenha } from './features/auth/redefinir-senha/redefinir-senha';

export const routes: Routes = [
    {
        path: 'login',
        component: Login, 
        title: 'Login | Autônomo Finanças'
    },
    {
        path: 'cadastro', 
        component: Cadastro, 
        title: 'Cadastro | Autônomo Finanças'
    },
    {
        path: 'esqueci-senha', 
        component: EsqueciSenha, 
        title: 'Recuperar senha | Autônomo Finanças'
    },
    {
        path: 'redefinir-senha', 
        component: RedefinirSenha, 
        title: 'Redefinir senha | Autônomo Finanças'
    },
    {
        path: '',
        component: MainLayout,
        canActivate: [authGuard],
        children: [
            {
                path: 'dashboard',
                component: Dashboard, 
                title: 'Dashboard | Autônomo Finanças'
            },
            {
                path: '',
                redirectTo: 'dashboard',
                pathMatch: 'full'
            },
            {
                path: 'lancamentos',
                component: Lancamentos, 
                title: 'Lançamentos | Autônomo Finanças'
            },
            {
                path: 'lancamentos/novo',
                component: FormLancamento, 
                title: 'Novo lançamento | Autônomo Finanças'
            },
            {
                path: 'lancamentos/:id/editar',
                component: FormLancamento, 
                title: 'Editar lançamento | Autônomo Finanças'
            },
            {
                path: 'categorias',
                component: Categorias, 
                title: 'Categorias | Autônomo Finanças'
            },
            {
                path: 'categorias/novo',
                component: FormCategoria, 
                title: 'Nova categoria | Autônomo Finanças'
            },
            {
                path: 'categorias/:id/editar',
                component: FormCategoria, 
                title: 'Editar categoria | Autônomo Finanças'
            },
            {
                path: 'plataformas',
                component: Plataformas, 
                title: 'Plataformas | Autônomo Finanças'
            },
            {
                path: 'plataformas/novo',
                component: FormPlataforma, 
                title: 'Nova plataforma | Autônomo Finanças'
            },
            {
                path: 'plataformas/:id/editar',
                component: FormPlataforma, 
                title: 'Editar plataforma | Autônomo Finanças'
            },
            {
                path: 'metas',
                component: Metas, 
                title: 'Metas | Autônomo Finanças'
            },
            {
                path: 'relatorios',
                component: Relatorios, 
                title: 'Relatórios | Autônomo Finanças'
            }, 
            {
                path: 'perfil',
                component: Perfil, 
                title: 'Perfil | Autônomo Finanças'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'dashboard'
    }
];
