import { Component } from '@angular/core';
import {ItemService} from "../../../services/item.service";
import {ItemDetails} from "../../../models/item/ItemDetails";

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent {

    items : ItemDetails[] = [];
    constructor(
        private itemService : ItemService
    ) { }

    ngOnInit(){
        this.getItems();
    }

     addItem(newItemName: string) {
        newItemName = newItemName.trim();
        if(newItemName == ""){return;}
        let newItem = new ItemDetails(newItemName,0);
        this.itemService.addItem(newItem).subscribe();
    }

     getItems() {
        this.itemService.getItems().subscribe(items => {
            this.items=items;
        });
    }

    deleteItem(item : ItemDetails){
        this.items = this.items.filter( i => i!==item);
        this.itemService.deleteItem(item.id).subscribe();
    }

}
