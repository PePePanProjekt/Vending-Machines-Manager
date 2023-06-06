export class UserInfo{
    userId: string;
    username: string;
    firstName: string
    lastName: string;
    phoneNumber: string;


    constructor(userId: string, username: string, firstName: string, lastName: string, phoneNumber: string) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
