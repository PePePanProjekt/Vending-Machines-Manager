import { Component } from '@angular/core';
import { Machine } from 'src/app/models/Machine';
import {MachineService} from "../../services/machine.service";

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.css']
})
export class MachinesComponent {

  machines: Machine[] = [];

  constructor(private machineService : MachineService){

  }

  ngOnInit(): void{
   this.getMachines();
  }

    private getMachines() {
        this.machineService.getMachines().subscribe(machines => this.machines = machines);
    }

    //todo confirmation
    deleteMachine(machine : Machine) {
        this.machines = this.machines.filter(m => m !== machine);
        this.machineService.deleteMachine(machine.id).subscribe();
    }
}
