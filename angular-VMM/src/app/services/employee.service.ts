import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {Employee} from "../models/Employee/Employee";
import {HttpClient} from "@angular/common/http";
import {registerLocaleData} from "@angular/common";
import {environment} from "../../environments/environment";
import {RegisterRequest} from "../models/Employee/RegisterRequest";

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {

    private registerURL = environment.apiUrl + `/api/auth/create`;
    private getAllUsersURL = environment.apiUrl + `/api/auth/users`;

    constructor(
        private http: HttpClient,
    ) {
    }

    getEmployees(): Observable<Employee[]> {
        return this.http.get<Employee[]>(this.getAllUsersURL);
    }

    addEmployee(newEmployee: Employee): Observable<Employee> {
        let role = newEmployee.roles[0].name.toLowerCase();

        let registerRequest = new RegisterRequest(
            newEmployee.username,
            newEmployee.password,
            newEmployee.firstName,
            newEmployee.lastName,
            newEmployee.phoneNumber,
            [role]);
        return this.http.post<Employee>(this.registerURL, registerRequest);
    }

    // deleteEmployee(id: string): Observable<Employee> {
    //     return of(EMPLOYEES.filter(e => e.id == id)[0]);
    // }
    //
    // getEmployee(id: string) {
    //     return of(EMPLOYEES.filter(e => e.id == id)[0])
    // }
    //
    // updateEmployee(employee: Employee) {
    //     return of(EMPLOYEES.filter(e => e === employee));
    // }
}
