import {Component} from '@angular/core';
import {Employee} from "../../../models/Employee/Employee";
import {EmployeeService} from "../../../services/employee.service";
import {Location} from "@angular/common";
import {RegisterRequest} from "../../../models/Employee/RegisterRequest";

@Component({
    selector: 'app-add-employee',
    templateUrl: './add-employee.component.html',
    styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {
    roles = ['Owner', 'Admin', 'Maintenance'];
    newEmployee: Employee = new Employee('', '', '', '', '', '',false, []);

    ngOnInit() {

    }

    constructor(
        private location: Location,
        private employeeService: EmployeeService,
    ) {
    }

    onSubmit() {
        this.employeeService.addEmployee(this.newEmployee).subscribe();
        this.goBack();
    }

    goBack() {
        this.location.back();
    }

}
