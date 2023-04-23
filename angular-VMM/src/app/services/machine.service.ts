import { Injectable } from '@angular/core';
import {catchError, Observable, of, tap} from "rxjs";
import {Machine} from "../models/Machine";
import {HttpClient, HttpClientModule, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {MachineInfo} from "../models/MachineInfo";

@Injectable({
  providedIn: 'root'
})
export class MachineService {
    private machineUrl  = "management-api/machines"
  constructor(private http: HttpClient) { }

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    addMachine(machine: Machine) : Observable<Machine> {
        return this.http.post<Machine>(environment.apiUrl+'/'+this.machineUrl,machine);
    }

    getMachines() : Observable<Machine[]> {
        return this.http.get<Machine[]>(environment.apiUrl+'/'+this.machineUrl).pipe(
            catchError(this.handleError<Machine[]>('getMachines',[]))
        );
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error : any): Observable<T> =>{
            console.error(error);
            return of(result as T);
        };
    }

    getMachine(id: string | null): Observable<MachineInfo> {
        const url = `${environment.apiUrl+'/'+this.machineUrl}/${id}`;
        return this.http.get<MachineInfo>(url).pipe(
            tap(_ =>console.log(`fetched machine id=${id}`)),
            catchError(this.handleError<MachineInfo>(`getMachine id=${id}`))
        );

    }

    deleteMachine(id: string) :Observable<Machine> {
        const url = `${environment.apiUrl+'/'+this.machineUrl}/${id}`;
        return this.http.delete<Machine>(url,this.httpOptions).pipe(
            tap(_ =>console.log(`deleted machine id=${id}`)),
            catchError(this.handleError<Machine>('deletedMachine'))
        );
    }
}
