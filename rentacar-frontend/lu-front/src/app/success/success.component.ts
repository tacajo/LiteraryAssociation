import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Transaction } from '../model/Transaction';
import { TransactionService } from '../service/transaction.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  constructor(private route : ActivatedRoute, private transactionService : TransactionService) { }
  
  transaction: Transaction = new Transaction();
  ngOnInit(): void {
   
    this.route.queryParams.subscribe(data=>{
      if(data.id !== undefined){
        if(data.way == "bitcoin") {
          this.success(data.id);
        } else if(data.way == "bank") {
          this.getTransaction(data.id);
        }
      }
    });
    

  }

  success(id : any){
    this.transactionService.successFailedBitcoin(true, id).subscribe();
    this.transactionService.successFailedBitcoinLu(true, id).subscribe();
  }

  getTransaction(id:any) {
    this.transactionService.getTransaction(id).subscribe(
      res=> {
        this.transaction = res;
        console.log(this.transaction);
      }, error => {
        console.log(error);
      });
  }
}
