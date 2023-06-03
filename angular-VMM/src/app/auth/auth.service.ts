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

    isUserInRole(roleFromRoute: string) {
        const roles = sessionStorage.getItem("app.roles");

        if (roles!.includes(",")) {
            if (roles === roleFromRoute) {
                return true;
            }
        } else {
            const roleArray = roles!.split(",");
            for (let role of roleArray) {
                if (role === roleFromRoute) {
                    return true;
                }
            }
        }
        return false;
    }
}
