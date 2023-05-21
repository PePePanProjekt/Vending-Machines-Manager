import { Component } from '@angular/core';
import {Employee} from "../../../models/Employee/Employee";
import {EmployeeService} from "../../../services/employee.service";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent {
    roles = ['Owner', 'Admin', 'Worker'];
    employee = new Employee('', '', '', '', '', '');

    constructor(
        private employeeService : EmployeeService,
        private location: Location,
        private route: ActivatedRoute,
    ) {
    }
    ngOnInit(){
        this.getEmployee();
    }

    onSubmit() {
        this.employeeService.updateEmployee(this.employee).subscribe();
        this.goBack();
    }

    goBack() {
        this.location.back();
    }

    private getEmployee(){
        const id :string = String(this.route.snapshot.paramMap.get('id'));
        this.employeeService.getEmployee(id).subscribe(e =>{
            this.employee = e;
        })
    }
}
