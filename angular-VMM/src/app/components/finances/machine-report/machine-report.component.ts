import { Component } from '@angular/core';
import {SingleItemStats} from "../../../models/Finance/SingleItemStats";
import {FinanceService} from "../../../services/finance.service";
import {Location} from "@angular/common";
import {MachineSimpleInfo} from "../../../models/Machine/MachineSimpleInfo";
import {MachineService} from "../../../services/machine.service";
import {SingleMachineStats} from "../../../models/Finance/SingleMachineStats";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-machine-report',
  templateUrl: './machine-report.component.html',
  styleUrls: ['./machine-report.component.css']
})
export class MachineReportComponent {
    toDate = new Date();
    sinceDate = new Date(this.toDate.getFullYear(), this.toDate.getMonth(), 1, 0, 0, 0, 0);
    allMachines: MachineSimpleInfo[] = [];
    selectedMachine?: string;
    machineStats?: SingleMachineStats;

    constructor(
        private financeService: FinanceService,
        private machineService: MachineService,
        private location: Location,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit() {
        this.getItems();
    }

    goBack() {
        this.location.back();
    }

    private getItems() {
        this.machineService.getMachines().subscribe(m => {
            this.allMachines = m;
            this.selectedMachine = m[0].id;
        });
    }

    private getMachineStats() {
        if (this.selectedMachine != null) {
            this.financeService.getMachineStats(this.sinceDate, this.toDate, this.selectedMachine).subscribe(stats => {
                this.machineStats = stats;
            })
        }
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
        this.getMachineStats();
    }
}
