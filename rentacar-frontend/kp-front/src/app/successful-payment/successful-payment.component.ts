import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentServiceService } from '../services/payment-service.service';

@Component({
  selector: 'app-successful-payment',
  templateUrl: './successful-payment.component.html',
  styleUrls: ['./successful-payment.component.css']
})
export class SuccessfulPaymentComponent implements OnInit {
  paymentId: string;
  PayerID: string;
  token: string;
  constructor(private route: ActivatedRoute, private paymentService : PaymentServiceService) {
    this.route.queryParams.subscribe(params => {
      this.paymentId = params['paymentId'];
      this.PayerID = params['PayerID'];
      this.token=params['token'];
  })
   }

  ngOnInit(): void {
    console.log(this.paymentId);
    console.log(this.PayerID);
    console.log(this.token);
    if(this.paymentId === undefined || this.PayerID === undefined || this.token === undefined){
      console.log('Bitcoin payment successful');
    }else{
      this.paymentService.completePayment(this.paymentId,this.PayerID,this.token).subscribe(
        res => {
          console.log(this.PayerID)
          console.log("completed");
        });
    }

    
  }

}
