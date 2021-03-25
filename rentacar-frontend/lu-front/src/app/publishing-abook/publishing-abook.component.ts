import { Component, OnInit } from '@angular/core';
import { FormField } from '../model/FormField';
import { FormValue } from '../model/FormValue';
import { UserService } from '../service/user.service';
import { WriterService } from '../service/writer.service';

@Component({
  selector: 'app-publishing-abook',
  templateUrl: './publishing-abook.component.html',
  styleUrls: ['./publishing-abook.component.css']
})
export class PublishingABookComponent implements OnInit {

  constructor(private writerService : WriterService,
              public userService: UserService) { }

  data : FormField = new FormField();
  formValues : FormValue[] = [];
  zanr : any;

  ngOnInit(): void {
    console.log(this.userService.getUsername());
    this.getFilds();
  }

  getFilds(){
    let username = this.userService.getUsername();
    this.writerService.publishingABook(username).subscribe(data => {
      this.data = data;
      console.log(this.data);

      for(let formField of this.data.formFields){
        let formValue = new FormValue();
        formValue.fieldId = formField.id;
        this.formValues.push(formValue);
      }

      this.data.formFields.forEach( (field : any) =>{
        if( field.type.name=='enum' && field.id=='zanr'){
          this.zanr = Object.keys(field.type.values);
        }
      });
    });
  }

  publish(){

    this.writerService.publish(this.formValues, this.data.taskId).subscribe(data =>{
      let username = this.userService.getUsername();
      localStorage.setItem('processId', this.data.processInstanceId);
      localStorage.setItem('publishingProcessId_' + username + '_' + this.formValues[0].fieldValue, this.data.processInstanceId);
      this.initData();
    });
  }

  initData() {
    for(let field of this.formValues) {
      field.fieldValue = "";
    }
  }

}
