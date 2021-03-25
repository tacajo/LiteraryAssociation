import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { User } from '../model/User';
import { PaymentServiceService } from '../services/payment-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private service : AuthService, private paymentService : PaymentServiceService) { }

  user : User = new User();

  ngOnInit(): void {
  }

  confirm(){
    this.service.confirmAcc(this.user).subscribe(data=>{
      console.log(data);
      var id = localStorage.getItem("uuid");

      this.paymentService.bitcoin('0.0001', 'mkHS9ne12qx9pS9VojpwU5xtRd4T7X7ZUt', id).subscribe(data=>{
        console.log(data);
        console.log('Successful payment!');
        window.location.href = 'success';
      }, error=>{
        alert('You don\'t have enough money!');
      });

    }, error => {
      console.log(error.error);

      alert(error.error);

    });
  }

}
