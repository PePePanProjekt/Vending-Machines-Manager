import { Component } from '@angular/core';
import {BatchDetails} from "../../../models/Batch/BatchDetails";
import {BatchService} from "../../../services/batch.service";
import {BatchSimple} from "../../../models/Batch/BatchSimple";
import {formatDate, registerLocaleData} from "@angular/common";
import localePl from '@angular/common/locales/pl';

@Component({
  selector: 'app-batches',
  templateUrl: './batches.component.html',
  styleUrls: ['./batches.component.css']
})
export class BatchesComponent {
    batches: BatchSimple[] = [];

    constructor(
        private batchService: BatchService
    ) {
        registerLocaleData(localePl); // Register the "pl" locale data
    }

    ngOnInit(){
        this.getBatches();
    }

    private getBatches(){
        this.batchService.getBatches().subscribe(batches => {
            this.batches = batches;
        })
    }

    deleteBatch(batch: BatchSimple){
        this.batches = this.batches.filter(b => b!==batch);
        this.batchService.deleteBatch(batch.id).subscribe();
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
