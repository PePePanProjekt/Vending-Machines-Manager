import {Component} from '@angular/core';
import {FinanceService} from "../../../services/finance.service";
import {ItemSimple} from "../../../models/item/ItemSimple";
import {ItemService} from "../../../services/item.service";
import {ItemDetails} from "../../../models/item/ItemDetails";
import {SingleItemStats} from "../../../models/Finance/SingleItemStats";
import {Location} from "@angular/common";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-item-report',
    templateUrl: './item-report.component.html',
    styleUrls: ['./item-report.component.css']
})
export class ItemReportComponent {
    toDate = new Date();
    sinceDate = new Date(this.toDate.getFullYear(), this.toDate.getMonth(), 1, 0, 0, 0, 0);
    allItems: ItemDetails[] = [];
    selectedItemId?: string;
    itemStats?: SingleItemStats;

    constructor(
        private financeService: FinanceService,
        private itemService: ItemService,
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
        this.itemService.getItems().subscribe(it => {
            this.allItems = it;
            this.selectedItemId = it[0].id;
        });
    }

    private getItemStats() {
        if (this.selectedItemId != null) {
            this.financeService.getItemStats(this.sinceDate, this.toDate, this.selectedItemId).subscribe(stats => {
                this.itemStats = stats;
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
        this.getItemStats();
    }

}
