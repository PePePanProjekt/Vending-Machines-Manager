import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {BatchService} from "../../../../services/batch.service";
import {ItemDetails} from "../../../../models/item/ItemDetails";
import {ItemSimple} from "../../../../models/item/ItemSimple";
import {ItemService} from "../../../../services/item.service";
import {HoldsDetails} from "../../../../models/Holds/HoldsDetails";

@Component({
  selector: 'app-add-batch',
  templateUrl: './add-batch.component.html',
  styleUrls: ['./add-batch.component.css']
})
export class AddBatchComponent {

    allItems?: ItemSimple[];
    itemsInBatch: HoldsDetails[] = [];

    //hold = new HoldDetails()

    constructor(
        private batchService: BatchService,
        private itemService: ItemService,
        private router : Router,
        private location : Location
    ) {}

    ngOnInit(){
        this.getItems();
    }
    goBack() {
        this.location.back();
    }

    private getItems(){
        this.itemService.getItems().subscribe(items => this.allItems = items);
    }

    addItemToBatch(item: ItemSimple | undefined, itemPrice: string, itemAmount: string){
        if(item ==null){return;} //todo error message
    }

}
