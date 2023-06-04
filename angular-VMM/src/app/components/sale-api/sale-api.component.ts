import {Component} from '@angular/core';
import {MachineFullInfo} from "../../models/Machine/MachineFullInfo";
import {MachineService} from "../../services/machine.service";
import {MachineSimpleInfo} from "../../models/Machine/MachineSimpleInfo";
import {Slot} from "../../models/Slot";
import {SaleService} from "../../services/sale.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-sale-api',
    templateUrl: './sale-api.component.html',
    styleUrls: ['./sale-api.component.css']
})
export class SaleApiComponent {
    allMachines: MachineSimpleInfo[] = [];
    selectedMachineId?: string;
    selectedSlotId?: number;
    slots: Slot[] = []

    constructor(
        private machineService: MachineService,
        private saleService: SaleService,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit() {
        this.getMachines();

    }

    private getMachines() {
        this.machineService.getMachines().subscribe(machines => {
                this.allMachines = machines
                this.selectedMachineId = this.allMachines[0].id;
                this.machineChange();
            }
        )
    }

    machineChange() {
        if (this.selectedMachineId != null) {
            this.machineService.getMachine(this.selectedMachineId).subscribe(info => {
                    this.slots = info.slots;
                    this.selectedSlotId = info.slots[0].slotNumber;
                }
            )
            console.log(this.selectedMachineId);
        }
    }

    sale() {
        if (this.selectedMachineId != null && this.selectedSlotId != null) {
            this.saleService.makeSale(this.selectedMachineId, this.selectedSlotId).subscribe();
            this.snackBar.open(
                `You bought item from machine:${this.allMachines.filter(m=>m.id==this.selectedMachineId)[0].name} slot:${this.selectedSlotId}`
            ,"OK")
        }
    }
}
