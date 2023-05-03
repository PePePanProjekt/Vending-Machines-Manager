import {Component} from '@angular/core';
import {ItemDetails} from "../../../../models/item/ItemDetails";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {ItemService} from "../../../../services/item.service";

@Component({
    selector: 'app-item-info',
    templateUrl: './item-info.component.html',
    styleUrls: ['./item-info.component.css']
})
export class ItemInfoComponent {
    item?: ItemDetails;

    constructor(
        private route: ActivatedRoute,
        private location: Location,
        private itemService: ItemService
    ) {}

    ngOnInit(){
        this.getItem();
    }

    goBack() {
        this.location.back();
    }

    private getItem() {
        const id :string = String(this.route.snapshot.paramMap.get('id'));
        this.itemService.getItem(id).subscribe(
            i => {
                this.item = i;
            });
    }
}
