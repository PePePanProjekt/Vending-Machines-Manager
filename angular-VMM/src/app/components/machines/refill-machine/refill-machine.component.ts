import {Component} from '@angular/core';
import {MachineFullInfo} from "../../../models/machine/MachineFullInfo";
import {MachineService} from "../../../services/machine.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Slot} from "../../../models/Slot";
import {ItemDetails} from "../../../models/item/ItemDetails";
import {ItemService} from "../../../services/item.service";

@Component({
    selector: 'app-refill-machine',
    templateUrl: './refill-machine.component.html',
    styleUrls: ['./refill-machine.component.css']
})
export class RefillMachineComponent {

    machine?: MachineFullInfo;
    slotList: Slot[] = [];
    newSlot: Slot = new Slot()
    allItems: ItemDetails[] = [];
    freeSlotIds: number[] = [];

    constructor(
        private machineService: MachineService,
        private itemsService: ItemService,
        private route: ActivatedRoute,
        private location: Location,
    ) {
    }

    ngOnInit() {
        this.newSlot.slotNumber = 0;
        this.newSlot.itemPrice = 0;
        this.newSlot.itemAmount = 0;
        // this.newSlot.itemId =
        // this.newSlot.itemName =
        this.getMachine();
        this.getItems()
    }

    private getMachine() {
        const id: string = String(this.route.snapshot.paramMap.get('id'));
        this.machineService.getMachine(id).subscribe(m => {
                this.machine = m;
                this.slotList = m.slots;
                let id = 0
                while (id<m.details.dispenserAmount){
                    this.freeSlotIds.push(id);
                    id++;
                }
                this.slotList.filter(slot => slot.slotNumber !in this.freeSlotIds)

            }
        )
    }

    private getItems(){
        this.itemsService.getItems().subscribe(
            items => {
                this.allItems = items;
                this.newSlot.itemId = items[0].id;
                this.newSlot.itemName = items[0].name;
            }
        )
    }

    goBack() {
        this.location.back();
    }



    addNewSlot() {
        let slot = new Slot()
        slot.slotNumber = this.newSlot.slotNumber;
        slot.itemId = this.newSlot.itemId;
        slot.itemAmount = this.newSlot.itemAmount;
        slot.itemPrice = this.newSlot.itemPrice;
        slot.itemName = this.newSlot.itemName;
        this.slotList.push(slot);
        this.freeSlotIds= this.freeSlotIds.filter(id => id != slot.slotNumber);
    }
}
