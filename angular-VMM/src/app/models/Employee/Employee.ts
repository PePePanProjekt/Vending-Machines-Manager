import {Role} from "./Role";

export class Employee{
    id: string;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    enabled: boolean;
    roles: Role[]


    constructor(id: string, username: string, password: string, firstName: string, lastName: string, phoneNumber: string, enabled: boolean, roles: Role[]) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.roles = roles;
    }
}


