import {Component} from '@angular/core';
import {DatePipe} from "@angular/common";
import {FinanceService} from "../../services/finance.service";
import {AllStats} from "../../models/Finance/AllStats";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-finances',
    templateUrl: './finances.component.html',
    styleUrls: ['./finances.component.css']
})
export class FinancesComponent {
    toDate = new Date();
    sinceDate = new Date(this.toDate.getFullYear(), this.toDate.getMonth(), 1, 0, 0, 0, 0);
    allStats?: AllStats;

    constructor(
        private financeService: FinanceService,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit() {
        this.getReport();
    }



    private getReport() {
        this.financeService.getAllStats(this.sinceDate, this.toDate).subscribe(report => {
            this.allStats = report;
        })
    }

    changeDates(newSince: string, newTo: string) {
        if(newSince == '' || newTo == ''){
            this.snackBar.open(
                `Please select a dates`
                ,"OK")
            return;
        }

        this.sinceDate = new Date(newSince);
        this.toDate = new Date(newTo);
        this.getReport();
    }
}
