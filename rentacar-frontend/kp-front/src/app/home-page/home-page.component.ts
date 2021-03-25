import { Component, OnInit } from '@angular/core';
import { PaymentServiceService } from '../services/payment-service.service';
import { CustomResponse } from '../model/CustomResponse';
import { ActivatedRoute } from '@angular/router';
import { PaymentWay } from '../model/PaymentWay';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  constructor(private paymentService: PaymentServiceService,
    private route: ActivatedRoute) {

    this.uuid = this.route.snapshot.paramMap.get('id');
    if (this.route.snapshot.paramMap.get('id') != null)
      localStorage.setItem("uuid", this.uuid);
    console.log("UUID = " + localStorage.getItem("uuid"));
  }

  checked: any;
  uuid: string = "";
  paymentWays : PaymentWay[] = [];

  ngOnInit(): void {
    this.checked = 'paypal';
    this.addPaymentWays();
  }

  continueToPayment(): void {
    console.log(this.checked);
    var id = localStorage.getItem("uuid");
    if (this.checked === 'BANK') {
      this.paymentService.addRequest(this.uuid).subscribe(
        res => {
          console.log(res);
          window.location.href = res['payment_url'];
        }, error => {
          console.log(error);
        }
      );

    } else if (this.checked === 'PAYPAL') {
      
      this.paymentService.payUsingPaypal('2').subscribe(
        res => {
          const response = res as CustomResponse;
          window.location.href = response.redirect_url;

        });
    } else {

        let id = localStorage.getItem("uuid");
        this.paymentService.bitcoinPayment('2', id).subscribe(data =>{
          console.log(data);
          window.location.href = data['redirect_url'];
        });
     
    }
  }

  addPaymentWays() {

    this.paymentService.getPaymentWays(this.uuid).subscribe(
      data => {
        console.log(data);
        this.paymentWays = data;
      }
    );
  }

}
