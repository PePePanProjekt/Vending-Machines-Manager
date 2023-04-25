import { Component } from '@angular/core';
import {MachineService} from "../../../services/machine.service";
import {Machine} from "../../../models/Machine";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Location} from "@angular/common";

@Component({
  selector: 'app-add-machine',
  templateUrl: './add-machine.component.html',
  styleUrls: ['./add-machine.component.css']
})
export class AddMachineComponent {

    constructor(
        private machineService : MachineService,
        private router : Router,
        private location : Location)
    {    }
    addMachine(location: string, name: string, dispenserAmount: string, dispenserDepth: string) {
        location = location.trim();
        name = name.trim();
        if(!location){return;}// todo error message
        this.machineService.addMachine(
            new Machine(location,name,+dispenserAmount,+dispenserDepth)
        ).subscribe();
        this.router.navigateByUrl("/machines");
    }

    goBack() {
        this.location.back();
    }
}
