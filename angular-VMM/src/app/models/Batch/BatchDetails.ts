import {HoldsDetails} from "../Holds/HoldsDetails";

export class BatchDetails {
    id?: string;
    date: string;
    name: string;
    holds: HoldsDetails[];


    constructor(date: string, name: string, holds: HoldsDetails[]) {
        this.date = date;
        this.name = name;
        this.holds = holds;
    }
}
