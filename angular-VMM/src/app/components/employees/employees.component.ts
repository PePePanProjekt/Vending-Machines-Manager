import {Component} from '@angular/core';
import {Employee} from "../../models/Employee/Employee";
import {EmployeeService} from "../../services/employee.service";

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
            this.owners = allEmployees.filter(e => e.role == 'OWNER');
            this.admins = allEmployees.filter(e => e.role == 'ADMIN');
            this.workers = allEmployees.filter(e => e.role == 'WORKER');
        });
    }

    deleteEmployee(id: string) {
        this.owners = this.owners.filter(e => e.id !== id);
        this.admins = this.admins.filter(e => e.id !== id);
        this.workers = this.workers.filter(e => e.id !== id);

        this.employeeService.deleteEmployee(id).subscribe();
    }
}
