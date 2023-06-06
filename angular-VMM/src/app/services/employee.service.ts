import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {Employee} from "../models/Employee/Employee";
import {HttpClient} from "@angular/common/http";
import {registerLocaleData} from "@angular/common";
import {environment} from "../../environments/environment";
import {RegisterRequest} from "../models/Employee/RegisterRequest";
import {UserInfo} from "../models/Employee/UserInfo";

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {

    private registerURL = environment.apiUrl + `/api/auth/create`;
    private getAllUsersURL = environment.apiUrl + `/api/auth/users`;
    private apiAuthURL = environment.apiUrl+`/api/auth`;

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

    getEmployee(id: string) {
        const url = this.apiAuthURL+`/users/${id}`;
        return this.http.get<UserInfo>(url);
    }

    updateEmployee(employee: RegisterRequest,employeeId: string) {
        let role = employee.roles[0].toLowerCase();
        employee.roles = [role];
        const url = this.apiAuthURL+`/users/${employeeId}`
        return this.http.put(url,employee);
    }

    getSelfInfo(){
        const url = this.apiAuthURL+`/self`;
        return this.http.get<UserInfo>(url);
    }
}
