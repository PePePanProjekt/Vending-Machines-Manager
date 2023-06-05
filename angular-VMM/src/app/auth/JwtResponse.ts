export class JwtResponse{
    id:string;
    username:string;
    roles: string[];
    accessToken: string;
    tokenType: string;


    constructor(id: string, username: string, roles: string[], accessToken: string, tokenType: string) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
