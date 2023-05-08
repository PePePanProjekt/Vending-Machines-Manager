import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {BatchService} from "../../../../services/batch.service";
import {ItemDetails} from "../../../../models/item/ItemDetails";
import {ItemSimple} from "../../../../models/item/ItemSimple";
import {ItemService} from "../../../../services/item.service";
import {HoldsDetails} from "../../../../models/Holds/HoldsDetails";
import {BatchDetails} from "../../../../models/Batch/BatchDetails";

@Component({
    selector: 'app-add-batch',
    templateUrl: './add-batch.component.html',
    styleUrls: ['./add-batch.component.css']
})
export class AddBatchComponent {

    allItems: ItemSimple[] = [];
    itemsInBatch: HoldsDetails[] = [];
    hold: HoldsDetails = new HoldsDetails("", 10, 10);

    constructor(
        private batchService: BatchService,
        private itemService: ItemService,
        private router: Router,
        private location: Location
    ) {
    }

    ngOnInit() {
        this.getItems();
    }

    goBack() {
        this.location.back();
    }

    private getItems() {
        this.itemService.getItems().subscribe(items => this.allItems = items);
    }

    addItemToBatch() {
        if (this.hold.itemId == "") {
            return;
        }
        console.log(this.hold);
        let newHold = new HoldsDetails(this.hold.itemId, this.hold.itemPrice, this.hold.itemAmount);
        this.itemsInBatch.push(newHold);
    }

    getItemName(itemId: string) {
        return this.allItems.filter(item => item.id == itemId)[0].name
    }

    deleteItemFromBatch(addedItem: HoldsDetails) {
        this.itemsInBatch = this.itemsInBatch.filter(item => item !== addedItem)
    }

    addBatch(date: string) {
        let newBatch = new BatchDetails(date, this.itemsInBatch);
        this.batchService.addBatch(newBatch).subscribe(b => {
                console.log(b.id);
            }
        );
        this.goBack();
    }
}
