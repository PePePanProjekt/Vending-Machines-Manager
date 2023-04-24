import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './auth/auth-guard.service';
import { GuestComponent } from './components/guest/guest.component';
import { MachinesComponent } from './components/machines/machines.component';
import { AddMachineComponent } from './components/machines/add-machine/add-machine.component';
import {MachineInfoComponent} from "./components/machines/machine-info/machine-info.component";

const routes: Routes = [
    {path:'', component:GuestComponent},
    {path: 'login', component: LoginComponent},
    {path:'machines', component:MachinesComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'machines/add', component:AddMachineComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'machines/:id', component: MachineInfoComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
