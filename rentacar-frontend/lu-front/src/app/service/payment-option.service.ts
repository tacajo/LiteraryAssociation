import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Merchant } from '../model/Merchant';

@Injectable({
  providedIn: 'root'
})
export class PaymentOptionService {

  constructor(private http: HttpClient) { }
  res : boolean = false;


  getPaymentOption() {
    return this.http.get<PaymentOptions>('http://localhost:8761/services');
  }

  getMerchant() {
    return this.http.get<Merchant>('http://localhost:8080/merchant');
  }
  isMerchantRegistred() {
    this.http.get<Merchant>('http://localhost:8080/merchant').subscribe(
      res => {
        console.log(res);
        this.res =  res.registered;
      }, error => {
        return false;
      }
    );
    return this.res;
  }

  registred() {
    return this.http.put('http://localhost:8080/merchant', 1);
  }

  registerKP() {
    var merchant = new Merchant();
    merchant.luport = 8080;
    merchant.luname = 'lu1';
    console.log("usao");
    return this.http.post<Merchant>('https://localhost:8443/payment-concentrator/merchant', merchant);
  }

  addPaymentWay(name: any) {
    return this.http.put('http://localhost:8080/merchant/payment-way', name);
  }

  addPaymentWayKP(name: any, uuid: any) {
    console.log(uuid);
    return this.http.put('https://localhost:8443/payment-concentrator/merchant/payment-way/' + uuid, name);
  }

  deletePaymentWay(name: any) {
    return this.http.put('http://localhost:8080/merchant/delete-payment-way', name);
  }

  deletePaymentWayKP(name: any, uuid: any) {
    return this.http.put('https://localhost:8443/payment-concentrator/merchant/delete-payment-way/' + uuid, name);
  }

  addUuid(uuid: any) {
    console.log("usao u add uid + uuid: " + uuid);
    return this.http.put('http://localhost:8080/merchant/uuid', uuid);
  }
}
