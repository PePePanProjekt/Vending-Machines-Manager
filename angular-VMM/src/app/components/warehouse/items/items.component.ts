import { Component } from '@angular/core';
import {ItemService} from "../../../services/item.service";
import {ItemDetails} from "../../../models/item/ItemDetails";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent {

    items : ItemDetails[] = [];
    constructor(
        private itemService : ItemService,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(){
        this.getItems();
    }

     addItem(newItemName: string) {
        newItemName = newItemName.trim();
        if(newItemName == ""){return;}
        let newItem = new ItemDetails(newItemName,0);
        this.itemService.addItem(newItem).subscribe();
        this.snackBar.open(`Added new item: ${newItem.name}`, "OK").onAction()
    }

     getItems() {
        this.itemService.getItems().subscribe(items => {
            this.items=items;
        });
    }

    deleteItem(item : ItemDetails){
        this.itemService.deleteItem(item.id).subscribe(details =>
            {
                if(details.id != null){
                    this.items = this.items.filter( i => i!==item);
                }
            }
        );
    }

}
