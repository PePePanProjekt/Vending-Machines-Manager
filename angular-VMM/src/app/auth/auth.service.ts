import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {LoginRequest} from "./LoginRequest";
import {JwtResponse} from "./JwtResponse";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient) {
    }

    isLoggedIn(): boolean {
        return sessionStorage.getItem("app.token") != null;
    }

    login(username: string, password: string) {
        // const httpOptions = {
        //     headers: {
        //         Authorization: 'Basic ' + window.btoa(username + ':' + password)
        //     },
        //     responseType: 'text' as 'text',
        // };
        let loginData = new LoginRequest(username, password);

        return this.http.post<JwtResponse>(environment.apiUrl + "/api/auth/signin", loginData);
    }

    logout() {
        sessionStorage.removeItem("app.token");
        sessionStorage.removeItem("app.roles");
    }

    isUserInRole(roleFromRoute: string): boolean {
        const roles = sessionStorage.getItem("app.roles");
        if (roles) {
            let rolesArray = roles.split(",");
            let success = false
            rolesArray.forEach(r=> {
                    if (roleFromRoute.includes(r)) {
                        success = true;
                    }
                }
            );
            return success;
        }
        return false;
    }



}
