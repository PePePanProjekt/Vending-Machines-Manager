import { Component } from '@angular/core';
import { Machine } from '../machine';

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.css']
})
export class MachinesComponent {

  machines!: Machine[];

  constructor(){
  
  }

  ngOnInit(): void{
    this.machines = [{
      "id": 1,
      "location": "Krasińskiego 8",
      "name": "Candy",
      "dispenserAmount": 10,
      "dispenserDepth": 10
    },
    {
      "id": 2,
      "location": "Żwirki i Wigury 17",
      "name": "Drinks",
      "dispenserAmount": 10,
      "dispenserDepth": 10
    },
    {
      "id": 3,
      "location": "Krasińskiego 8",
      "name": "Candy",
      "dispenserAmount": 10,
      "dispenserDepth": 10
    }];
  }

}
