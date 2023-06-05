export class RegisterRequest{
    username: string;
    password: string;
    roles: string[];


    constructor(username: string, password: string, roles: string[]) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
