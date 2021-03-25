import { Book } from "./Book";

export class Transaction {
    uuid: string = "";
    amount: number = 0;
    id: number = 0;
    luport: number = 0;
    luUuid: any;
    username: String = '';
    books: Book[] = [];
  }