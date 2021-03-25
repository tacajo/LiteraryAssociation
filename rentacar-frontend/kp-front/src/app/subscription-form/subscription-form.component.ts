import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SubscriberData } from '../model/SubscriberData';
import { User } from '../model/User';
import { AuthService } from '../services/auth.service';
import { PaymentServiceService } from '../services/payment-service.service';

@Component({
  selector: 'app-subscription-form',
  templateUrl: './subscription-form.component.html',
  styleUrls: ['./subscription-form.component.css']
})
export class SubscriptionFormComponent implements OnInit {
  subscriber: SubscriberData = new SubscriberData();
  constructor(private paymentService: PaymentServiceService, private loginService: AuthService,  private route: ActivatedRoute) { 
    
    this.uuidLU = this.route.snapshot.paramMap.get('id');
  }

  firstform: boolean = false;
  secondform: boolean = false;
  login: boolean = true;
  checked: any;
  user: User = new User();
  uuidLU: string = "";
  ngOnInit(): void {
    this.checked = 'gY';
  }

  confirm() {
    this.loginService.confirmAcc(this.user).subscribe(data => {
      this.login=false;
      this.firstform=true;
      var id = localStorage.getItem("uuid");
    }, error => {
      console.log(error.error);

      alert(error.error);

    } )};

    continue() {
      this.firstform = false;
      this.secondform = true;
    }
    continueToSubscription() {
      this.paymentService.subscribe(this.checked, this.subscriber,this.uuidLU).subscribe(
        res => {
          var r = JSON.parse(JSON.stringify(res));

          window.location.href = r['links'][0]['href'];

        });
    }
  }
