import { Injectable } from '@angular/core';
import {catchError, Observable, of, tap} from "rxjs";
import {Machine} from "../models/Machine";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {MachineFullInfo} from "../models/MachineFullInfo";
import {MachineSimpleInfo} from "../models/MachineSimpleInfo";

@Injectable({
  providedIn: 'root'
})
export class MachineService {
    private machineUrl  = environment.apiUrl+"/api/management/machines"
  constructor(private http: HttpClient) { }

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    addMachine(machine: Machine) : Observable<Machine> {
        return this.http.post<Machine>(this.machineUrl,machine);
    }

    getMachines() : Observable<MachineSimpleInfo[]> {
        return this.http.get<MachineSimpleInfo[]>(this.machineUrl).pipe(
            catchError(this.handleError<MachineSimpleInfo[]>('getMachines',[]))
        );
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error : any): Observable<T> =>{
            console.error(error);
            return of(result as T);
        };
    }

    getMachine(id: string): Observable<MachineFullInfo> {
        const url = `${this.machineUrl}/${id}`;
        return this.http.get<MachineFullInfo>(url).pipe(
            tap(_ =>console.log(`service fetched machine id=${id}`)),
            catchError(this.handleError<MachineFullInfo>(`getMachine id=${id}`))
        );

    }

    deleteMachine(id: string) :Observable<MachineSimpleInfo> {
        const url = `${this.machineUrl}/${id}`;
        return this.http.delete<MachineSimpleInfo>(url,this.httpOptions).pipe(
            tap(_ =>console.log(`deleted machine id=${id}`)),
            catchError(this.handleError<MachineSimpleInfo>('deletedMachine'))
        );
    }

    updateMachine(machine: Machine) {
        return this.http.put(this.machineUrl, machine,this.httpOptions).pipe(
            tap(_ =>console.log(`updated machine id=${machine.id}`)),
            catchError(this.handleError<Machine>('updatedMachine'))
        )
    }
}
