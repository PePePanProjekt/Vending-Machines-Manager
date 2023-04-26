import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {LoginComponent} from './components/login/login.component';
import {FormsModule} from '@angular/forms';
import {AuthInterceptor} from './auth/auth.interceptor';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { LayoutModule } from '@angular/cdk/layout';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { NavbarOwnerComponent } from './components/navbar/navbar-owner/navbar-owner.component';
import { GuestComponent } from './components/guest/guest.component';
import { MachinesComponent } from './components/machines/machines.component';
import { AddMachineComponent } from './components/machines/add-machine/add-machine.component';
import { NavbarGuestComponent } from './components/navbar/navbar-guest/navbar-guest.component';
import { MachineInfoComponent } from './components/machines/machine-info/machine-info.component';
import { UpdateMachineComponent } from './components/machines/update-machine/update-machine.component';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        GuestComponent,
        MachinesComponent,
        AddMachineComponent,
        NavbarGuestComponent,
        NavbarOwnerComponent,
        MachineInfoComponent,
        UpdateMachineComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatMenuModule,
        MatSidenavModule,
        MatTableModule,
        MatButtonModule,
        MatInputModule,
        MatFormFieldModule,
        MatSnackBarModule,
        LayoutModule,
        MatIconModule,
        MatListModule,
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}