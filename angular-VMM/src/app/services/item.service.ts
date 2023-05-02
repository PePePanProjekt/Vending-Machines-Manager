import {Injectable} from '@angular/core';
import {ItemDetails} from "../models/item/ItemDetails";
import {catchError, Observable, of, tap} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ItemService {

    private itemUrl  =environment.apiUrl+`/api/management/items`;

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(
        private http: HttpClient
    ) {}


    addItem(newItem: ItemDetails): Observable<ItemDetails> {
        return this.http.post<ItemDetails>(this.itemUrl, newItem);
    }

    getItems() : Observable<ItemDetails[]>{
        return this.http.get<ItemDetails[]>(this.itemUrl);
    }

    deleteItem(id: string) :Observable<ItemDetails> {
        const url = `${this.itemUrl}/${id}`;
        return this.http.delete<ItemDetails>(url,this.httpOptions).pipe(
            tap(_ =>console.log(`deleted item id=${id}`)),
            catchError(this.handleError<ItemDetails>('deletedItem'))
        );
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error : any): Observable<T> =>{
            console.error(error);
            return of(result as T);
        };
    }
}
