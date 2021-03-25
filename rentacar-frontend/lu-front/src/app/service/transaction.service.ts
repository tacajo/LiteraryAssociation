import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../model/Transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient) { }

  addTransaction(amount: number) {
    return this.http.post<Transaction>('http://localhost:8080/transaction', amount);
  }

  addKPtransaction(trans: Transaction) {
    return this.http.post('https://localhost:8443/payment-concentrator/transaction', trans);
  }

  addKPtransactiona() {
    console.log("usao")
    return this.http.get('https://localhost:8443/payment/add');
  }

  successFailedBitcoin(flag : any, id : any): Observable<any>{
    return this.http.put(`https://localhost:8443/bitcoin/bitcoin/${flag}/${id}`, {}, {responseType:'text'});
  }  

  successFailedBitcoinLu(flag : any, id : any): Observable<any>{
    return this.http.put(`http://localhost:8080/transaction/${flag}/${id}`, {}, {responseType:'text'});
  } 

  getTransaction(uuid: any) {
    return this.http.get<Transaction>('http://localhost:8080/transaction/'+ uuid);
  }

}
