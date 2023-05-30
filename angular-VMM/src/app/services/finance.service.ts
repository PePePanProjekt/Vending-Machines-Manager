import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AllStats} from "../models/Finance/AllStats";
import {StatsRequest} from "../models/Finance/StatsRequest";
import {formatDate, registerLocaleData} from "@angular/common";
import {dateTimestampProvider} from "rxjs/internal/scheduler/dateTimestampProvider";
import localePl from '@angular/common/locales/pl';
@Injectable({
  providedIn: 'root'
})
export class FinanceService {

    private financeUrl  =environment.apiUrl+`/api/finance/`;

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(
        private http: HttpClient,

    ) {
        registerLocaleData(localePl); // Register the "pl" locale data
    }

    getAllStats(sinceDate: Date, toDate: Date) {
        const startDateString = this.formatDate(sinceDate);
        const stopDateString = this.formatDate(toDate);
        let url = this.financeUrl + 'all/' + startDateString + '/' + stopDateString;
        return this.http.get<AllStats>(url);
    }

    private formatDate(date: Date): string {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

}
