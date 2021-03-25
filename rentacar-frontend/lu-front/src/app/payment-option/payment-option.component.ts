import { Component, OnInit } from '@angular/core';
import { Merchant } from '../model/Merchant';
import { PaymentOption } from '../model/PaymentOption';
import { PaymentOptionService } from '../service/payment-option.service';

@Component({
  selector: 'app-payment-option',
  templateUrl: './payment-option.component.html',
  styleUrls: ['./payment-option.component.css']
})
export class PaymentOptionComponent implements OnInit {

  constructor(private paymentService : PaymentOptionService) { }

  registred : boolean = true;
  temp : any = null;
  paymentOption: PaymentOption = new PaymentOption();
  merchant : Merchant = new Merchant();


  ngOnInit(): void {
    this.isRegistred();
    console.log(this.registred);
    this.getOption();
  }

  isRegistred() {
    this.paymentService.getMerchant().subscribe(
      res => {
        this.registred = res.registered;
        this.merchant = res;
      }
    );
  }

  isSubscribed(option: any) {
    for(var o of this.merchant.paymentWays) {
      if(o == option)
        return true;
    }
    return false;
  }

  register() {
    this.paymentService.registred().subscribe(
      res => {
        this.ngOnInit();
        this.paymentService.registerKP().subscribe(res => {
          this.paymentService.addUuid(res.lu).subscribe();
        });
      }
    );
    
  }

  getOption() {
    this.paymentService.getPaymentOption().subscribe(
      res => {
        console.log(res);
        this.temp = res;
        this.paymentOption = this.temp;
      }
    );
  }

  subscribe(option: any) {
    this.paymentService.addPaymentWay(option).subscribe(
      res => {
        this.ngOnInit();
        this.paymentService.getMerchant().subscribe(
          res=> {
            console.log(res);
            this.paymentService.addPaymentWayKP(option, res.uuid).subscribe(
              );
          }
        );
        
      }
    );
  }

  unsubscribe(option: any) {
    this.paymentService.deletePaymentWay(option).subscribe(
      res => {
        this.ngOnInit();
        this.paymentService.deletePaymentWayKP(option, this.merchant.uuid).subscribe();
      }
    );
  }

}
