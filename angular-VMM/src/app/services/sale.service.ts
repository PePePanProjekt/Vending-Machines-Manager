import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
    private saleUrl = environment.apiUrl + "/api/external"

    constructor(private http: HttpClient) {
    }

    makeSale(machineId: string, slotId: number){
       let url = this.saleUrl+'/sale'
        return this.http.get<String>(url +`/${machineId}/${slotId}`);
    }
}
