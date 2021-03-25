import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormValue } from '../model/FormValue';
import { Writer } from '../model/Writer';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-confirm-account',
  templateUrl: './confirm-account.component.html',
  styleUrls: ['./confirm-account.component.css']
})
export class ConfirmAccountComponent implements OnInit {
  
  token: string = "";
  writer : Writer = new Writer();
  selectedFiles! : FileList;
  formData : FormData = new FormData;
  formValues : FormValue[] = [];
  taskId : any;

  constructor(private activatedRoute: ActivatedRoute, private service: UserService) { }

  ngOnInit(): void {

    this.activatedRoute.queryParams.subscribe(params => {
      console.log(params['ct']);
      this.token = params['ct'];
    });
    
    this.confirmAcc();
    
  }

  confirmAcc(){
    let processId = localStorage.getItem('processId');
     console.log(processId);
    this.service.confirmAccount(this.token, processId).subscribe(
      data =>{
        this.writer = data;
        let processId = localStorage.getItem('processId');
        // this.service.getPdfForm(processId).subscribe(data =>{
        //   console.log(data);
        //   this.taskId = data;
        //   this.taskId = this.taskId["taskId"];
        // });
      },
      error => {
      console.log(error);
      }
     )
  }

  savePdfs(){
    
    for(let i = 0; i < this.selectedFiles.length; i++){
      if(!(this.selectedFiles[i].type.match("application/pdf"))){
        alert('You can upload only pdf files.');
        return;
      }
    }
    if(this.selectedFiles.length <2) {
      alert('You have to upload at least 2 pdfs.');
      return;
    }

    for(let i = 0; i < this.selectedFiles.length; i++){
      console.log(this.selectedFiles[i]);
      this.formData.append('pdfs', this.selectedFiles[i]);
    }

    this.service.savePdfs(this.formData, this.writer.username, this.taskId).subscribe(
      res => {
        alert('successful');
        window.location.href = 'http://localhost:4200';
      }, error => {
        
        if(error["statusText"] == "OK") {
          alert('successful');
          window.location.href = 'http://localhost:4200';
        }
        
      }
    );
  }

  selectFiles(event : any) {
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles)
  }

}
