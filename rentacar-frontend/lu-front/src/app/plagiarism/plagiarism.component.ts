import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormField } from '../model/FormField';
import { FormValue } from '../model/FormValue';
import { User } from '../model/User';
import { PlagiarismService } from '../service/plagiarism.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-plagiarism',
  templateUrl: './plagiarism.component.html',
  styleUrls: ['./plagiarism.component.css']
})
export class PlagiarismComponent implements OnInit {

  constructor(private userService: UserService, private router: Router, private plagiarismService: PlagiarismService) { }

  data: FormField = new FormField();
  formValues: FormValue[] = [];
  user: User = new User();

  ngOnInit(): void {
    this.getFields();
  }


  getFields() {
    this.plagiarismService.getFields().subscribe(data => {
      console.log(this.data);
      this.data = data;
      localStorage.setItem('processId', this.data.processInstanceId);
      console.log(this.data);


      for (let formField of this.data.formFields) {
        let formValue = new FormValue();
        formValue.fieldId = formField.id;
        this.formValues.push(formValue);
      }
    });

  }

confirm() {
    console.log(this.formValues);
    this.plagiarismService.confirmPlagiarism(this.formValues, this.data.taskId).subscribe(data => {
      this.initData();
    },
    error => {
      console.log(error);
      alert('All marked fileds are required.');
     
      this.initData();

    });
}

  initData() {
    for (let field of this.formValues) {
      field.fieldValue = "";
    }
  }

  validation(field: any) {
    for (let vc of field.validationConstraints) {
      if (vc.name == 'required') {
        return true;
      }
    }
    return false;
  }
}
