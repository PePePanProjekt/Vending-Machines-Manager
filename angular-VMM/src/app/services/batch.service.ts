import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {BatchDetails} from "../models/Batch/BatchDetails";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {BatchSimple} from "../models/Batch/BatchSimple";

@Injectable({
  providedIn: 'root'
})
export class BatchService {

    private batchUrl  =environment.apiUrl+`/api/warehouse/batch`;
  constructor(
      private http: HttpClient
  ) { }

    getBatches() :Observable<BatchSimple[]> {
        return this.http.get<BatchSimple[]>(this.batchUrl+"/simple");
    }
}
