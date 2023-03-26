import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddMachineComponent } from './add-machine/add-machine.component';
import { GuestComponent } from './guest/guest.component';
import { LoginComponent } from './login/login.component';
import { MachinesComponent } from './machines/machines.component';

const routes: Routes = [
{path:'login', component:LoginComponent},
{path:'', component:GuestComponent},
{path:'machines', component:MachinesComponent},
{path:'machines/add', component:AddMachineComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
