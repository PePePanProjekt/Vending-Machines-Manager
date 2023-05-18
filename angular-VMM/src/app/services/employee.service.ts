import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {Employee, EMPLOYEES} from "../models/Employee/Employee";
import {ItemDetails} from "../models/item/ItemDetails";

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

}
