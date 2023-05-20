import { Component } from '@angular/core';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-finances',
  templateUrl: './finances.component.html',
  styleUrls: ['./finances.component.css']
})
export class FinancesComponent {
    toDate = new Date();
    sinceDate = new Date(this.toDate.getFullYear(),this.toDate.getMonth(), 1,0,0,0,0);
    income  = 0;
    expenses = 0;
    balance = 0;
    bestMachineId = ""
    bestProductName = ""
    constructor(
    ) {    }
    ngOnInit(){
        this.getReport();
    }

    private getReport() {
        //this.financeService.getData.subscribe(data => {set fields})
        this.income = 5000;
        this.expenses = -4000;
        this.balance = this.income + this.expenses;
        this.bestMachineId = "1fcc0ab4-6c2e-4545-9436-a50c7861fed0";
        this.bestProductName = "kabanosy";
    }

    changeDates(newSince: string, newTo: string) {
        this.sinceDate = new Date(newSince);
        this.toDate = new Date(newTo);
    }
}
