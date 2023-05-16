import {Component} from '@angular/core';
import {Machine} from "../../../models/machine/Machine";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {MachineService} from "../../../services/machine.service";
import {MachineFullInfo} from "../../../models/machine/MachineFullInfo";
@Component({
    selector: 'app-machine-info',
    templateUrl: './machine-info.component.html',
    styleUrls: ['./machine-info.component.css']
})
export class MachineInfoComponent {
    machine?:MachineFullInfo;
    constructor(
        private route: ActivatedRoute,
        private location: Location,
        private machineService: MachineService
    ) {
    }

    ngOnInit() {
        this.getMachine();
    }

    private getMachine() {
        const id :string = String(this.route.snapshot.paramMap.get('id'));
        this.machineService.getMachine(id).subscribe(
            machineInfo => {
                this.machine = machineInfo;
            });
    }

    goBack() {
        this.location.back();
    }
}
