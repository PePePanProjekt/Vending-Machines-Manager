import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

    constructor(private router: Router,
                private snackBar: MatSnackBar) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = sessionStorage.getItem("app.token");
        if (token) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                },
            });
        }

        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => this.handleErrorRes(error))
        );
    }

    private handleErrorRes(error: HttpErrorResponse): Observable<never> {
        if (error.status === 401) {
            //this.router.navigateByUrl("/login", {replaceUrl: true});
            this.snackBar.open(
                `You don't have the access to this page`
                ,"OK")
        }
        return throwError(() => error);
    }
}
