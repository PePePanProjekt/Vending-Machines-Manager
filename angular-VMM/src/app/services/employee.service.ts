import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {Employee, EMPLOYEES} from "../models/Employee/Employee";
import {HttpClient} from "@angular/common/http";
import {registerLocaleData} from "@angular/common";
import {environment} from "../../environments/environment";
import {RegisterRequest} from "../models/Employee/RegisterRequest";

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {

    private registerURL  =environment.apiUrl+`/api/auth/create`;
    constructor(
        private http: HttpClient,
    ) {}

    getEmployees(): Observable<Employee[]> {
        return  of(EMPLOYEES);
    }

    addEmployee(newEmployee: Employee): Observable<Employee> {
        let role = "";
        if(newEmployee.role =="Owner"){ role = 'ROLE_OWNER'}
        else if (newEmployee.role =="Admin") {role = 'ROLE_ADMIN'}
        else role = 'ROLE_MAINTENANCE'


        let registerRequest = new RegisterRequest(newEmployee.email,"password",[role]);
        return this.http.post<Employee>(this.registerURL, registerRequest);
    }

    deleteEmployee(id: string): Observable<Employee> {
        return of(EMPLOYEES.filter(e=> e.id == id)[0]);
    }

    getEmployee(id: string) {
        return of(EMPLOYEES.filter(e => e.id == id)[0])
    }

    updateEmployee(employee: Employee) {
        return of(EMPLOYEES.filter(e => e ===employee));
    }
}
