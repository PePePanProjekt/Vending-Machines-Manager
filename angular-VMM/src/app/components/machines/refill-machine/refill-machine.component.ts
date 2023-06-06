import {Component} from '@angular/core';
import {MachineFullInfo} from "../../../models/Machine/MachineFullInfo";
import {MachineService} from "../../../services/machine.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Slot} from "../../../models/Slot";
import {ItemDetails} from "../../../models/item/ItemDetails";
import {ItemService} from "../../../services/item.service";

@Component({
    selector: 'app-refill-Machine',
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
        this.newSlot.itemPrice = 10;
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
                while (id < m.details.dispenserAmount) {
                    this.freeSlotIds.push(id);
                    id++;
                }

                // removes taken slots from freeSlots[]
                this.freeSlotIds = this.freeSlotIds.filter(
                    freeSlot => !this.machine?.slots.some(
                        machineSlot => machineSlot.slotNumber ===freeSlot))
            }
        )
    }

    private getItems() {
        this.itemsService.getItems().subscribe(
            items => {
                this.allItems = items;
                this.newSlot.itemId = items[0].id;
                this.newSlot.itemName = items[0].name;
                this.newSlot.slotNumber = this.freeSlotIds[0];
            }
        )
    }

    goBack() {
        this.location.back();
    }


    //sorry for this
    addNewSlot() {
        if (this.freeSlotIds.length == 0) return;
        if (this.newSlot.slotNumber != null && this.freeSlotIds.includes(this.newSlot.slotNumber)) {
            let slot = new Slot()
            slot.slotNumber = this.newSlot.slotNumber;
            slot.itemId = this.newSlot.itemId;
            slot.itemAmount = this.newSlot.itemAmount;
            if (this.newSlot.itemAmount != null &&
                (this.newSlot.itemAmount <= 0 ||
                    (this.allItems.filter(
                        item => item.id == this.newSlot.itemId)[0].amountAvailable - this.newSlot.itemAmount < 0))) return;
            if (this.newSlot.itemPrice != null) slot.itemPrice = +(Math.round(this.newSlot.itemPrice * 100) / 100).toFixed(2);
            if (slot.itemPrice != null && slot.itemPrice < 0) return;
            slot.itemName = this.allItems.filter(item => item.id == this.newSlot.itemId)[0].name;
            this.slotList.push(slot);
            if (this.newSlot.itemAmount != null)
                this.allItems.filter(
                    item => this.newSlot.itemId == item.id)[0].amountAvailable -= this.newSlot.itemAmount;
            this.freeSlotIds = this.freeSlotIds.filter(id => id != slot.slotNumber);
        }
        this.newSlot.slotNumber = this.freeSlotIds[0];
        if (this.newSlot.itemAmount != null) this.newAmount(this.newSlot.itemAmount.toString())
    }

    newAmount(value: string) {
        if (this.machine != null) {
            let newAmount = +value;
            let maxAmount =
                this.machine.details.dispenserDepth
                >
                this.allItems.filter(item => item.id == this.newSlot.itemId)[0].amountAvailable
                    ?
                    this.allItems.filter(item => item.id == this.newSlot.itemId)[0].amountAvailable
                    :
                    this.machine.details.dispenserDepth;

            if (newAmount > maxAmount) {
                newAmount = maxAmount;
            } else if (newAmount < 0) {
                newAmount = 0;
            }
            this.newSlot.itemAmount = newAmount;
        }
    }

    deleteSlot(slot: Slot) {
        if (slot.itemAmount != null) {
            this.allItems.filter(item => item.id == slot.itemId)[0].amountAvailable += slot.itemAmount;
        }
        if (slot.slotNumber != null) {
            this.freeSlotIds.push(slot.slotNumber);
            this.freeSlotIds.sort();
        }
        this.slotList = this.slotList.filter(s => s !== slot);

    }

    SubmitSlots() {
        const id = String(this.route.snapshot.paramMap.get('id'));
        this.machineService.refill(id, this.slotList).subscribe();
        this.goBack();
    }
}
