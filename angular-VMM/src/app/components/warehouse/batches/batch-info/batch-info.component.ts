import {Component} from '@angular/core';
import {BatchDetails} from "../../../../models/Batch/BatchDetails";
import {BatchService} from "../../../../services/batch.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {ItemSimple} from "../../../../models/item/ItemSimple";
import {ItemService} from "../../../../services/item.service";

@Component({
    selector: 'app-batch-info',
    templateUrl: './batch-info.component.html',
    styleUrls: ['./batch-info.component.css']
})
export class BatchInfoComponent {
    batch?: BatchDetails;
    allItems: ItemSimple[] = []
    constructor(
        private route: ActivatedRoute,
        private itemService: ItemService,
        private location: Location,
        private batchService: BatchService,
    ) {
    }

    ngOnInit() {
        this.getBatch();
        this.getItems();
    }

    private getBatch() {
        const id: string = String(this.route.snapshot.paramMap.get('id'));
        this.batchService.getBatch(id).subscribe(
            b => {
                this.batch = b;
            });
    }

    private getItems() {
        this.itemService.getItems().subscribe(items => this.allItems = items);
    }

    goBack() {
        this.location.back();
    }

    getItemName(itemId: string) {
        return this.allItems.filter(item => item.id == itemId)[0].name
    }
    formatDateString(dateString: string): string {
        const date = new Date(dateString);
        const options: Intl.DateTimeFormatOptions = {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            timeZone: 'UTC' // Set the desired time zone here
        };
        const formattedDate = date.toLocaleString('en-GB', options).replace(',', '');
        return formattedDate;
    }
}
