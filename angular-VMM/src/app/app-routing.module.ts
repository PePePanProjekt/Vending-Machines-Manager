import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './auth/auth-guard.service';
import { GuestComponent } from './components/guest/guest.component';
import { MachinesComponent } from './components/machines/machines.component';
import { AddMachineComponent } from './components/machines/add-machine/add-machine.component';
import {MachineInfoComponent} from "./components/machines/machine-info/machine-info.component";
import {UpdateMachineComponent} from "./components/machines/update-machine/update-machine.component";
import {WarehouseComponent} from "./components/warehouse/warehouse.component";
import {ItemsComponent} from "./components/warehouse/items/items.component";
import {ItemInfoComponent} from "./components/warehouse/items/item-info/item-info.component";
import {BatchesComponent} from "./components/warehouse/batches/batches.component";
import {UpdateItemComponent} from "./components/warehouse/items/update-item/update-item.component";

const routes: Routes = [
    {path:'', component:GuestComponent},
    {path: 'login', component: LoginComponent},
    {path:'machines', component:MachinesComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'machines/add', component:AddMachineComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'machines/:id', component: MachineInfoComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'machines/update/:id', component: UpdateMachineComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'warehouse', component:WarehouseComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'warehouse/items', component:ItemsComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'warehouse/batches', component:BatchesComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'warehouse/items/:id', component:ItemInfoComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path:'warehouse/items/update/:id', component:UpdateItemComponent, canActivate: [AuthGuard], data: {role: 'ROLE_USER'}},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
