export class Machine {
    id: string = "-1";
    location: string;
    name: string;
    dispenserAmount: number;
    dispenserDepth: number;


    constructor(location: string, name: string, dispenserAmount: number, dispenserDepth: number) {
        this.location = location;
        this.name = name;
        this.dispenserAmount = dispenserAmount;
        this.dispenserDepth = dispenserDepth;
    }
}
