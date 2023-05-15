import {HoldsDetails} from "../Holds/HoldsDetails";

export class BatchDetails {
    id?: string;
    date: string;
    holds: HoldsDetails[];


    constructor(date: string, holds: HoldsDetails[]) {
        this.date = date;
        this.holds = holds;
    }
}
