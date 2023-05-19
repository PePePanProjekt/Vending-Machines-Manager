import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {Employee, EMPLOYEES} from "../models/Employee/Employee";

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {
    constructor() {
    }

    getEmployees(): Observable<Employee[]> {
        return  of(EMPLOYEES);
    }

    addEmployee(newEmployee: Employee): Observable<Employee> {
        EMPLOYEES.push(newEmployee);
        return  of(newEmployee);
    }

    deleteEmployee(id: string): Observable<Employee> {
        return of(EMPLOYEES.filter(e=> e.id == id)[0]);
    }
}
