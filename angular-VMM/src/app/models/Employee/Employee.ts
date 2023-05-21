export class Employee{
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    role: string


    constructor(id: string, firstName: string, lastName: string, email: string, phoneNumber: string, role: string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}

export const EMPLOYEES: Employee[] = [
    {
        id: '1',
        firstName: 'Jan',
        lastName: 'Nowak',
        email: 'jannowak@gmail.com',
        phoneNumber: "123 123 123",
        role: 'OWNER'
    },
    {
        id: '2',
        firstName: 'Jakub',
        lastName: 'Kowalski',
        email: 'jakubkowalski@gmail.com',
        phoneNumber: '111 222 333',
        role: 'ADMIN'
    },
    {
        id: '3',
        firstName: 'Adam',
        lastName: 'Wiśniewski',
        email: 'adamwsniewski@gmail.com',
        phoneNumber: '444 555 666',
        role: 'WORKER'
    },
    {
        id: '4',
        firstName: 'Jarosław',
        lastName: 'Kubica',
        email: 'jaroslawkubica@gmail.com',
        phoneNumber: '777 888 999',
        role: 'WORKER'
    },
];
