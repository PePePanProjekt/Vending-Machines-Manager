import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { GuestComponent } from './guest/guest.component';
import { MachinesComponent } from './machines/machines.component';
import { AddMachineComponent } from './add-machine/add-machine.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    GuestComponent,
    MachinesComponent,
    AddMachineComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
