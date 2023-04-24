import {Component, Input} from '@angular/core';
import {Machine} from "../../../models/Machine";
import {MachineService} from "../../../services/machine.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-update-machine',
  templateUrl: './update-machine.component.html',
  styleUrls: ['./update-machine.component.css']
})
export class UpdateMachineComponent {
    @Input() machine?: Machine;

    constructor(
        private machineService : MachineService,
        private route : ActivatedRoute
    ) {
    }

    ngOnInit(){
        this.getMachine();
    }

    private getMachine() {
        const id :string = String(this.route.snapshot.paramMap.get('id'));
        this.machineService.getMachine(id).subscribe(
            machineInfo => {
                this.machine = machineInfo.details;
            });
    }

    save() {
        if (this.machine) {
            this.machineService.updateMachine(this.machine)
                .subscribe();
        }
    }


}
