import {Component, Input} from '@angular/core';
import {Machine} from "../../../models/Machine";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {MachineService} from "../../../services/machine.service";
import {MachineInfo} from "../../../models/MachineInfo";

@Component({
  selector: 'app-machine-info',
  templateUrl: './machine-info.component.html',
  styleUrls: ['./machine-info.component.css']
})
export class MachineInfoComponent {
     machineInfo? : MachineInfo;

    constructor(
        private route : ActivatedRoute,
        private location : Location,
        private machineService : MachineService
    ) {}

    ngOnInit(): void{
        this.getMachine();
    }

    private getMachine() : void {
        const id = this.route.snapshot.paramMap.get('id');
        this.machineService.getMachine(id).subscribe(machineInfo => this.machineInfo =  machineInfo);
    }
}
