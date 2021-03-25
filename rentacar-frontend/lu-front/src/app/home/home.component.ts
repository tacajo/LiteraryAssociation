import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from '../model/Book';
import { FormValue } from '../model/FormValue';
import { Merchant } from '../model/Merchant';
import { Transaction } from '../model/Transaction';
import { Writer } from '../model/Writer';
import { PaymentOptionService } from '../service/payment-option.service';
import { TransactionService } from '../service/transaction.service';
import { UserService } from '../service/user.service';
import { WriterService } from '../service/writer.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private transactionService: TransactionService,
    private paymentService: PaymentOptionService,
    private route: ActivatedRoute,
    private router: Router,
    public userService: UserService,
    private writerService: WriterService,
    private _snackBar: MatSnackBar) { }

  userName: String = "";
  imageURL: string = "assets/images/library-icon.png";
  isCollapsed: boolean = false;
  cart: boolean = false;
  home: boolean = true;
  publishABook: boolean = false;
  tasksEditor: boolean = false;
  payment: boolean = false;
  opinion: boolean = false;
  uuid: string = "";
  sum: number = 2;
  merchant: Merchant = new Merchant();
  uploadPdfsPage: boolean = false;
  processId: any;
  writer: Writer = new Writer();
  selectedFiles!: FileList;
  formData: FormData = new FormData;
  formValues: FormValue[] = [];
  taskId: any;
  waiting: boolean = false;
  editorPlagiarisms : boolean = false;
  original : boolean = false;
  acceptBookTask : boolean = false;
  sendBetaReaders : boolean = false;
  chooseBetaReader : boolean = false;
  addCommentForm : boolean = false;
  downloadBooksEditor : boolean = false;
  editorForPlagiarism : boolean = false;
  reportPlagiarsmFlag : boolean = false;
  addingNotes : boolean = false;
  books : Book[] = [];
  amount : number = 0;

  ngOnInit(): void {
    this.userService.initUser();
    if (this.userService.tokenIsNull()) {
      this.router.navigateByUrl("");
    }
    this.processId = localStorage.getItem('processId');
    console.log(this.userService.getRoleName());
    this.getAllBooks();
    this.getMerchentInfo();
    console.log(this.merchant);
  }

  getAllBooks() {
    this.userService.getBooks().subscribe(
      res=> {
        this.books = res;
        console.log(this.books);
      }, error => {
        console.log(error);
      }
    );
  }

  addToCard(bookId: any) {
    this.userService.addBookToCart(bookId).subscribe(
      res =>{
        this._snackBar.open("Book is added to cart", 'x', {
          duration: 2000,
        });
      }
    );
  }

  logOut() {
    this.userService.removeToken();
    this.router.navigateByUrl("");
  }

  isAdmin() {
    return this.userService.getRoleName() == "admin";
  }

  isComitteeMember() {
    return this.userService.getRoleName() == "committee_member";
  }

  isWriter() {
    return this.userService.getRoleName() == "writer";
  }

  isEditor() {
    return this.userService.getRoleName() == "editor";
  }
  isReader() {
    return this.userService.getRoleName() == "reader";
  }

  getMerchentInfo() {
    this.paymentService.getMerchant().subscribe(
      res => {
        this.merchant = res;
      }
    );
  }

  editorForPlagiarismChoose(){
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = true;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;

  }

  publishingABook() {
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = true;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;

  }

  originals(){
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = true;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  uploadPdfs() {
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = true;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  editorPlagiarism(){
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = true;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  editorTasks() {
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = true;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  showPaymentOption() {
    this.cart = false;
    this.home = false;
    this.payment = true;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false

  }

  showCart() {
    this.cart = true;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
    this.userService.getCart().subscribe(
      res => {
        this.amount = 0;
        this.books = res;
        if(this.books.length > 0) {
          for(let book of this.books) {
            this.amount += book.price;
          }
        }
        
      }, error =>{
        console.log(error);
      }
    );
  }

  showHome() {
    this.cart = false;
    this.home = true;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
    this.getAllBooks();
  }

  showGiveOpinion() {
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = true;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  acceptBook() {
    this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;
    this.acceptBookTask = true;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  sendToBetaReaders(){
      this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = true;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }
  
  chooseBetaR() {
      this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = true;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  downloadBooks(){
      this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = true;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }
  addComment() {
  this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = true;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = false;
  }

  reportPlagiarism(){
  this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = true;
    this.addingNotes = false;

  }

  addNotes(){
     this.cart = false;
    this.home = false;
    this.payment = false;
    this.opinion = false;
    this.publishABook = false;
    this.tasksEditor = false;
    this.uploadPdfsPage = false;
    this.editorPlagiarisms = false;
    this.original = false;   
    this.acceptBookTask = false;
    this.sendBetaReaders = false;
    this.chooseBetaReader = false;
    this.addCommentForm  = false;
    this.downloadBooksEditor = false;
    this.editorForPlagiarism = false;
    this.reportPlagiarsmFlag = false;
    this.addingNotes = true;
  }

  pay() {

    this.transactionService.addTransaction(this.amount).subscribe(
      res => {
        localStorage.setItem('transactionId', this.uuid);
        this.uuid = res.uuid;
        console.log(this.uuid);
        var t = new Transaction();
        t.luport = 8080;
        t.amount = this.amount;
        t.uuid = this.uuid;
        t.username = res.username;
        this.paymentService.getMerchant().subscribe(
          res => {
            this.merchant = res;
            t.luUuid = this.merchant.uuid;
            console.log(this.merchant);

            console.log(t);
            this.transactionService.addKPtransaction(t).subscribe(
              res => {
                window.location.href = 'http://localhost:4201/payment/' + this.uuid;
              }
            );
          }
        );




      }
    );
    //this.router.navigate(['.'], { relativeTo: this.route, queryParams: { ... }});

  }

  payFee() {
    // this.userService.payMembershipPee().subscribe(
    //   res => {
    //     console.log(res);
    //   }, error => {
    //     console.log(error);
    //     if(error["error"]["text"] == "no task") {
    //       this._snackBar.open("The 14-day period has expired.", 'x', {
    //         duration: 2000,
    //       });
    //     }
    //   }
    // );
    this.writerService.pay().subscribe(
      res => {
        console.log(res);
      }, error => {
        console.log(error);

      }
    );
  }



  savePdfs() {

    this.userService.initUser();
    for (let i = 0; i < this.selectedFiles.length; i++) {
      if (!(this.selectedFiles[i].type.match("application/pdf"))) {
        alert('You can upload only pdf files.');
        return;
      }
    }
    if (this.selectedFiles.length < 2) {
      alert('You have to upload at least 2 pdfs.');
      return;
    }

    for (let i = 0; i < this.selectedFiles.length; i++) {
      console.log(this.selectedFiles[i]);
      this.formData.append('pdfs', this.selectedFiles[i]);
    }
    this.waiting = true;
    this.userService.savePdfsWriter(this.formData).subscribe(
      res => {
        this.waiting = false;
        this._snackBar.open("You have successfully added pdfs.", 'x', {
          duration: 2000,
        });

      }, error => {
        this.waiting = false;
        if (error["statusText"] == "OK") {
          this._snackBar.open("You have successfully added pdfs.", 'x', {
            duration: 2000,
          });
        }

      }
    );
  }

  subscribe()
  {
    this.paymentService.getMerchant().subscribe(
      res => {
        this.merchant = res;
        console.log(this.merchant);
        window.location.href = 'http://localhost:4201/subscription-form/' + this.merchant.uuid;
         
      }
    );

  }

  selectFiles(event: any) {
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles)
  }
}
