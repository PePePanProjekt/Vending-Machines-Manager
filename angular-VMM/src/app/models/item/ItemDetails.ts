export class ItemDetails {
    id!: string;
    name: string;
    amountAvailable: number;


    constructor(name: string, amountAvailable: number) {
        this.name = name;
        this.amountAvailable = amountAvailable;
    }
}
