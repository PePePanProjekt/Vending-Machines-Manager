import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {BatchDetails} from "../models/Batch/BatchDetails";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BatchService {

    private batchUrl  =environment.apiUrl+`/api/warehouse/batch`;
  constructor(
      private http: HttpClient
  ) { }

    getBatches() :Observable<BatchDetails[]> {
        return this.http.get<BatchDetails[]>(this.batchUrl);
    }
}
