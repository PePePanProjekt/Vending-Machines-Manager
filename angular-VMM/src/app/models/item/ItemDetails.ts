export class ItemDetails {
    id!: string;
    name: string;
    amountAvailable: number = 0;


    constructor(name: string) {
        this.name = name;
    }
}
