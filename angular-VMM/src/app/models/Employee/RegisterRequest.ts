export class RegisterRequest {
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    roles: string[];


    constructor(username: string, password: string, firstName: string, lastName: string, phoneNumber: string, roles: string[]) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
