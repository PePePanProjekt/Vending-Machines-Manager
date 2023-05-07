import { Component } from '@angular/core';
import {BatchDetails} from "../../../models/Batch/BatchDetails";
import {BatchService} from "../../../services/batch.service";
import {BatchSimple} from "../../../models/Batch/BatchSimple";

@Component({
  selector: 'app-batches',
  templateUrl: './batches.component.html',
  styleUrls: ['./batches.component.css']
})
export class BatchesComponent {
    batches?: BatchSimple[];

    constructor(
        private batchService: BatchService
    ) {}

    ngOnInit(){
        this.getBatches();
    }

    private getBatches(){
        this.batchService.getBatches().subscribe(batches => this.batches = batches)
    }

}
