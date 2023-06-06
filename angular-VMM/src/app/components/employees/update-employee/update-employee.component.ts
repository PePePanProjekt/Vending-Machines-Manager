import {Component} from '@angular/core';
import {Employee} from "../../../models/Employee/Employee";
import {EmployeeService} from "../../../services/employee.service";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {UserInfo} from "../../../models/Employee/UserInfo";
import {RegisterRequest} from "../../../models/Employee/RegisterRequest";

@Component({
    selector: 'app-update-employee',
    templateUrl: './update-employee.component.html',
    styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent {
    roles = ['Owner', 'Admin', 'Maintenance'];
    employee = new RegisterRequest('', '', '', '', '', ['']);

    constructor(
        private employeeService: EmployeeService,
        private location: Location,
        private route: ActivatedRoute,
    ) {
    }

    ngOnInit() {
        this.getEmployee();
    }

    onSubmit() {
        const id :string = String(this.route.snapshot.paramMap.get('id'));
        this.employeeService.updateEmployee(this.employee,id).subscribe();
        this.goBack();
    }

    goBack() {
        this.location.back();
    }

    private getEmployee() {
        const id: string = String(this.route.snapshot.paramMap.get('id'));
        this.employeeService.getEmployee(id).subscribe(e => {
            this.employee.firstName = e.firstName;
            this.employee.lastName = e.lastName;
            this.employee.username = e.username;
            this.employee.phoneNumber = e.phoneNumber
        })
    }
}
