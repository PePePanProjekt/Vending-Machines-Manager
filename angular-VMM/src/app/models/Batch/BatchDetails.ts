import {HoldsDetails} from "../Holds/HoldsDetails";

export interface BatchDetails {
    id: string;
    date: string;
    holds: HoldsDetails[];
}
