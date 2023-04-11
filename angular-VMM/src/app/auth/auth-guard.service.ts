import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard{

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if (this.authService.isLoggedIn() && this.authService.isUserInRole(next.routeConfig?.data?.['role'])) {
          return true;
      } else {
          this.router.navigateByUrl("/login");
          return false;
      }
  }
}