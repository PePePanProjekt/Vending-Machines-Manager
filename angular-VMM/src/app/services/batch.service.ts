import {Injectable} from '@angular/core';
import {catchError, Observable, of, tap} from "rxjs";
import {BatchDetails} from "../models/Batch/BatchDetails";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {BatchSimple} from "../models/Batch/BatchSimple";
import {ItemDetails} from "../models/item/ItemDetails";

@Injectable({
    providedIn: 'root'
})
export class BatchService {

    private batchUrl = environment.apiUrl + `/api/warehouse/batch`;

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    constructor(
        private http: HttpClient
    ) {
    }

    getBatches(): Observable<BatchSimple[]> {
        return this.http.get<BatchSimple[]>(this.batchUrl + "/simple");
    }


    addBatch(newBatch: BatchDetails): Observable<BatchDetails> {
        return this.http.post<BatchDetails>(this.batchUrl, newBatch);
    }

    deleteBatch(id: string) {
        let deleteUrl = this.batchUrl + "/" + id;
        return this.http.delete<ItemDetails>(deleteUrl).pipe(
            tap(_ => console.log(`deleted batch id=${id}`)),
            catchError(this.handleError<ItemDetails>('deletedBatch'))
        );
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            console.error(error);
            return of(result as T);
        };
    }

    getBatch(id: string): Observable<BatchDetails> {
        let infoUrl = this.batchUrl + "/" + id;
        return this.http.get<BatchDetails>(infoUrl);
    }

    updateBatch(updatedBatch: BatchDetails) :Observable<BatchDetails> {
        return this.http.put<BatchDetails>(this.batchUrl,updatedBatch,this.httpOptions);
    }
}
