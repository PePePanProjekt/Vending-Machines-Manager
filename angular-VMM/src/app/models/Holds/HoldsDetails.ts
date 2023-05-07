export class HoldsDetails{
    id?: string;
    itemId: string;
    itemPrice: number;
    itemAmount: number;


    constructor(itemId: string, itemPrice: number, itemAmount: number) {
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.itemAmount = itemAmount;
    }
}
