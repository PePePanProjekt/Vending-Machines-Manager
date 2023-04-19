import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  //standalone: true,
  selector: 'app-navbar-owner',
  templateUrl: './navbar-owner.component.html',
  styleUrls: ['./navbar-owner.component.css']
})
export class NavbarOwnerComponent {

  constructor(private authService: AuthService, private router: Router) { }

  logout() {
    this.authService.logout();

    this.router.navigateByUrl("/");

  }

}
