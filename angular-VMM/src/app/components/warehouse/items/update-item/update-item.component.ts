import {Component, Input} from '@angular/core';
import {ItemDetails} from "../../../../models/item/ItemDetails";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {ItemService} from "../../../../services/item.service";

@Component({
  selector: 'app-update-item',
  templateUrl: './update-item.component.html',
  styleUrls: ['./update-item.component.css']
})
export class UpdateItemComponent {
    @Input()item?: ItemDetails;

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

    save() {
        if (this.item) {
            this.itemService.updateItem(this.item)
                .subscribe();
        }
        this.goBack();
    }
}
