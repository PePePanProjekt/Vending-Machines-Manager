import {Component} from '@angular/core';
import {Employee} from "../../models/Employee/Employee";
import {EmployeeService} from "../../services/employee.service";
import {getLocaleFirstDayOfWeek} from "@angular/common";

@Component({
    selector: 'app-employees',
    templateUrl: './employees.component.html',
    styleUrls: ['./employees.component.css']
})
export class EmployeesComponent {
    owners: Employee[] = [];
    admins: Employee[] = [];
    workers: Employee[] = [];

    constructor(
        private employeeService: EmployeeService,
    ) {
    }

    ngOnInit() {
        this.getEmployees();
    }

    private getEmployees() {
        this.employeeService.getEmployees().subscribe(allEmployees => {
            allEmployees = allEmployees.filter( e => e.enabled);

            this.owners = allEmployees.filter(e => e.roles[0].name == 'ROLE_OWNER');
            this.admins = allEmployees.filter(e => e.roles[0].name == 'ROLE_ADMIN');
            this.workers = allEmployees.filter(e => e.roles[0].name == 'ROLE_MAINTENANCE');
        });
    }

    deleteEmployee(id: string) {


        this.employeeService.deleteEmployee(id).subscribe(info=>
        {
            if(info!=null){
                this.owners = this.owners.filter(e => e.id !== id);
                this.admins = this.admins.filter(e => e.id !== id);
                this.workers = this.workers.filter(e => e.id !== id);
            }
        });
    }
}
