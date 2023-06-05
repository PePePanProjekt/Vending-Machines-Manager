import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.service';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard{

  constructor(
      private authService: AuthService,
      private router: Router,
      private snackBar: MatSnackBar) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if (this.authService.isLoggedIn() && this.authService.isUserInRole(next.routeConfig?.data?.['role'])) {
          return true;
      } else {
          this.snackBar.open(
              `You don't have the access to this page`
              ,"OK")
          //this.router.navigateByUrl("/login");
          return false;
      }
  }
}
