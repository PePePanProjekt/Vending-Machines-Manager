import {Component, Input} from '@angular/core';
import {BatchService} from "../../../../services/batch.service";
import {ItemService} from "../../../../services/item.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import {ItemSimple} from "../../../../models/item/ItemSimple";
import {HoldsDetails} from "../../../../models/Holds/HoldsDetails";
import {BatchDetails} from "../../../../models/Batch/BatchDetails";

@Component({
  selector: 'app-update-batch',
  templateUrl: './update-batch.component.html',
  styleUrls: ['./update-batch.component.css']
})
export class UpdateBatchComponent {

    allItems: ItemSimple[] = [];
    itemsInBatch: HoldsDetails[] = [];
    hold: HoldsDetails = new HoldsDetails("", 10, 10);
    @Input() batch?: BatchDetails;
    oldDate: string = "";
    constructor(
        private route: ActivatedRoute,
        private batchService: BatchService,
        private itemService: ItemService,
        private router: Router,
        private location: Location
    ) {
    }

    ngOnInit() {
        this.getItems();
        this.getBatch();
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
        //console.log(this.hold);
        let newHold = new HoldsDetails(this.hold.itemId, this.hold.itemPrice, this.hold.itemAmount);
        this.itemsInBatch.push(newHold);
    }

    getItemName(itemId: string) {
        return this.allItems.filter(item => item.id == itemId)[0].name
    }

    deleteItemFromBatch(addedItem: HoldsDetails) {
        this.itemsInBatch = this.itemsInBatch.filter(item => item !== addedItem)
    }

    private getBatch() {
        const id: string = String(this.route.snapshot.paramMap.get('id'));
        this.batchService.getBatch(id).subscribe(
            b => {
                this.batch = b;
                this.itemsInBatch = b.holds;
                this.oldDate = b.date
            });
    }


    updateBatch(oldDate: boolean, newDate: string) {
        let updatedBatch = new BatchDetails(this.oldDate, this.itemsInBatch);
        updatedBatch.id = String(this.route.snapshot.paramMap.get('id'));
        if (!oldDate) updatedBatch.date = newDate;
        this.batchService.updateBatch(updatedBatch).subscribe();
        this.goBack();

    }
}
