import {Component} from '@angular/core';
import {EmployeeService} from "../../services/employee.service";
import {UserInfo} from "../../models/Employee/UserInfo";
import {Location} from "@angular/common";

@Component({
    selector: 'app-self-info',
    templateUrl: './self-info.component.html',
    styleUrls: ['./self-info.component.css']
})
export class SelfInfoComponent {

    user?: UserInfo;

    constructor(
        private employeeService: EmployeeService,
        private location: Location
    ) {
    }

    ngOnInit() {
        this.getInfo();
    }

    private getInfo() {
        this.employeeService.getSelfInfo().subscribe(info =>
            this.user = info)
    }

    goBack() {
        this.location.back();
    }
}
