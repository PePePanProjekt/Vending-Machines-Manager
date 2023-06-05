import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './auth/auth-guard.service';
import { MachinesComponent } from './components/machines/machines.component';
import { AddMachineComponent } from './components/machines/add-machine/add-machine.component';
import {MachineInfoComponent} from "./components/machines/machine-info/machine-info.component";
import {UpdateMachineComponent} from "./components/machines/update-machine/update-machine.component";
import {WarehouseComponent} from "./components/warehouse/warehouse.component";
import {ItemsComponent} from "./components/warehouse/items/items.component";
import {ItemInfoComponent} from "./components/warehouse/items/item-info/item-info.component";
import {BatchesComponent} from "./components/warehouse/batches/batches.component";
import {UpdateItemComponent} from "./components/warehouse/items/update-item/update-item.component";
import {AddBatchComponent} from "./components/warehouse/batches/add-batch/add-batch.component";
import {BatchInfoComponent} from "./components/warehouse/batches/batch-info/batch-info.component";
import {UpdateBatchComponent} from "./components/warehouse/batches/update-batch/update-batch.component";
import {RefillMachineComponent} from "./components/machines/refill-machine/refill-machine.component";
import {EmployeesComponent} from "./components/employees/employees.component";
import {AddEmployeeComponent} from "./components/employees/add-employee/add-employee.component";
import {UpdateEmployeeComponent} from "./components/employees/update-employee/update-employee.component";
import {FinancesComponent} from "./components/finances/finances.component";
import {SaleApiComponent} from "./components/sale-api/sale-api.component";
import {ItemReportComponent} from "./components/finances/item-report/item-report.component";
import {MachineReportComponent} from "./components/finances/machine-report/machine-report.component";

const routes: Routes = [
    {path:'',                               component:LoginComponent},
    {path:'login',                          component: LoginComponent},

    {path:'sale-api',                       component:SaleApiComponent,         canActivate:[AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},

    {path:'finances',                       component:FinancesComponent,        canActivate: [AuthGuard], data: {role: 'ROLE_OWNER'}},
    {path:'finances/item',                  component:ItemReportComponent,      canActivate: [AuthGuard], data: {role: 'ROLE_OWNER'}},
    {path:'finances/machine',               component:MachineReportComponent,   canActivate: [AuthGuard], data: {role: 'ROLE_OWNER'}},

    {path:'employees',                      component:EmployeesComponent,       canActivate: [AuthGuard], data: {role: 'ROLE_OWNER'}},
    {path:'employees/add-employee',         component:AddEmployeeComponent,     canActivate: [AuthGuard], data: {role: 'ROLE_OWNER'}},
    {path:'employees/update/:id',           component:UpdateEmployeeComponent,  canActivate: [AuthGuard], data: {role: 'ROLE_OWNER'}},

    {path: 'machines',                      component: MachinesComponent,       canActivate: [AuthGuard],  data: {role: ['ROLE_OWNER', 'ROLE_ADMIN', 'ROLE_MAINTENANCE']}},
    {path:'machines/add',                   component:AddMachineComponent,      canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN', 'ROLE_MAINTENANCE']}},
    {path:'machines/update/:id',            component:UpdateMachineComponent,   canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN', 'ROLE_MAINTENANCE']}},
    {path:'machines/refill/:id',            component:RefillMachineComponent,   canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN', 'ROLE_MAINTENANCE']}},
    {path:'machines/:id',                   component:MachineInfoComponent,     canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN', 'ROLE_MAINTENANCE']}},

    {path:'warehouse',                      component:WarehouseComponent,       canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path:'warehouse/batches',              component:BatchesComponent,         canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path:'warehouse/batches/add',          component:AddBatchComponent,        canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path:'warehouse/batches/:id',          component:BatchInfoComponent,       canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path:'warehouse/batches/update/:id',   component:UpdateBatchComponent,     canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},

    {path:'warehouse/items',                component:ItemsComponent,           canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path:'warehouse/items/:id',            component:ItemInfoComponent,        canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path:'warehouse/items/update/:id',     component:UpdateItemComponent,      canActivate: [AuthGuard], data: {role: ['ROLE_OWNER', 'ROLE_ADMIN']}},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
