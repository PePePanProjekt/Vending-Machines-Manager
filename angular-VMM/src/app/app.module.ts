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
import { WarehouseComponent } from './components/warehouse/warehouse.component';
import { ItemsComponent } from './components/warehouse/items/items.component';
import { ItemInfoComponent } from './components/warehouse/items/item-info/item-info.component';
import { BatchesComponent } from './components/warehouse/batches/batches.component';
import { UpdateItemComponent } from './components/warehouse/items/update-item/update-item.component';
import { AddBatchComponent } from './components/warehouse/batches/add-batch/add-batch.component';
import { BatchInfoComponent } from './components/warehouse/batches/batch-info/batch-info.component';
import { UpdateBatchComponent } from './components/warehouse/batches/update-batch/update-batch.component';
import { RefillMachineComponent } from './components/machines/refill-machine/refill-machine.component';
import { EmployeesComponent } from './components/employees/employees.component';
import { AddEmployeeComponent } from './components/employees/add-employee/add-employee.component';

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
        UpdateMachineComponent,
        WarehouseComponent,
        ItemsComponent,
        ItemInfoComponent,
        BatchesComponent,
        UpdateItemComponent,
        AddBatchComponent,
        BatchInfoComponent,
        UpdateBatchComponent,
        RefillMachineComponent,
        EmployeesComponent,
        AddEmployeeComponent,
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
