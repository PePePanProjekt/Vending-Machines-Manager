import { Component } from '@angular/core';
import {MachineService} from "../../services/machine.service";
import {MachineSimpleInfo} from "../../models/Machine/MachineSimpleInfo";

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.css']
})
export class MachinesComponent {

  machines: MachineSimpleInfo[] = [];

  constructor(private machineService : MachineService){

  }

  ngOnInit(): void{
   this.getMachines();
  }

    private getMachines() {
        this.machineService.getMachines().subscribe(machines => this.machines = machines);
    }

    //todo confirmation
    deleteMachine(machine : MachineSimpleInfo) {
        this.machines = this.machines.filter(m => m !== machine);
        this.machineService.deleteMachine(machine.id).subscribe();
    }
}
