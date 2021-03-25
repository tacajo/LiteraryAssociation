import { Component, OnInit } from '@angular/core';
import { Account } from '../model/Account';
import { PaymentServiceService } from '../services/payment-service.service';

@Component({
  selector: 'app-bank-login',
  templateUrl: './bank-login.component.html',
  styleUrls: ['./bank-login.component.css']
})
export class BankLoginComponent implements OnInit {

  constructor(private paymentService: PaymentServiceService) { }

  account: Account = new Account();

  ngOnInit(): void {
  }

  onClick() {
    this.paymentService.confirm(localStorage.getItem("uuid"), this.account).subscribe(
      res =>{
        console.log("prosao", res);
        var url = res.url;
        console.log(url.toString());
        window.location.href = url.toString() + "?id=" + localStorage.getItem("uuid") + "&way=bank";
      }, error =>{
        console.log("error", error);
      }
    );

  }

}
