import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from "rxjs/operators"; 
import { ResultTransaction } from '../model/ResultTransaction';
import { PaymentWay } from '../model/PaymentWay';
import { SubscriberData } from '../model/SubscriberData';
@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  constructor(private http: HttpClient) { }

  payUsingPaypal(sum : String) {  
    return this.http.post('https://localhost:8443/paypal/paypal/make/payment?sum='+sum, {})
    .pipe(map(res => res));
  }

  subscribe(checked: string, subscriber : SubscriberData,uuidLU: string) {  
    console.log(subscriber);
    return this.http.post('http://localhost:8084/paypal/subscribe?subsType='+checked+'&uuidLU='+ uuidLU, subscriber)
    .pipe(map(res => res));
  }
 
  completePayment(paymentId, payerId,token) {
    return this.http.post('https://localhost:8443/paypal/paypal/complete/payment?paymentId=' + paymentId + '&token='+ token+ '&PayerID=' + payerId + '&uuid=' + localStorage.getItem("uuid") , {})
    .pipe(map(res => res));
  }

  bitcoin(amount : any, address : any, uuid : any){
    return this.http.post(`https://localhost:8443/bitcoin/bitcoin/send/${amount}/${address}/${uuid}`, {});
  }

  bitcoinPayment(amount : any, uuid : any){
    return this.http.post(`https://localhost:8443/bitcoin/bitcoin/${amount}/${uuid}`, {});
  }

  addRequest(uuid: any) {
    return this.http.get(`https://localhost:8443/payment-concentrator/request/${uuid}`);
  }

  confirm(uuid: any, account: any) {
    return this.http.post<ResultTransaction>('https://localhost:8443/bank/acquirer/check/' + uuid, account);
  }

  getPaymentWays(uuid: any) {
    return this.http.get<PaymentWay[]>("https://localhost:8443/payment-concentrator/merchant/payment-way/" + uuid);
  }
}
