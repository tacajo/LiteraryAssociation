import { IfStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { FormField } from '../model/FormField';
import { FormValue } from '../model/FormValue';
import { Genre } from '../model/Genre';
import { User } from '../model/User';
import { FormService } from '../service/form.service';
import { RegistrationService } from '../service/registration.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private registerService: RegistrationService,
    private userService: UserService, private router: Router,
    private formService: FormService,
    private _snackBar: MatSnackBar) { }

  data: FormField = new FormField();
  formValues: FormValue[] = [];
  user: User = new User();
  genres: Genre[] = [];
  betaReader: boolean = false;
  reg: boolean = true;
  processId: string = '';
  visible: boolean = false;

  ngOnInit(): void {
    this.reg = true;
  }

  // getFields() {
  //   this.initData();
  //   this.registerService.startRegWriter().subscribe(
  //     data=>{
  //       console.log(data);
  //       this.registerService.getFields(data).subscribe(data => {
  //         this.reg = true;
  //         this.data = data;
  //         localStorage.setItem('processId', this.data.processInstanceId);
    
  //         for (let formField of this.data.formFields) {
  //           let formValue = new FormValue();
  //           formValue.fieldId = formField.id;
  //           this.formValues.push(formValue);
  //           this.genres = [];
            
  //           if (formField.type.name == 'enum' && formField.id == 'genre') {
  //             console.log(formField.type.values);
  //             for (let g in formField.type.values) {
  //               var genre = new Genre();
  //               genre.value = g;
  //               genre.checked = false;
  //               console.log(g);
  //               this.genres.push(genre);
  //             }
  //           }
  //         }
  //       });
  //     }, error => {
  //       console.log(error);
  //       this.registerService.getFields(error["error"]["text"]).subscribe(data => {
  //         this.reg = true;
  //         this.data = data;
  //         localStorage.setItem('processId', this.data.processInstanceId);
    
  //         for (let formField of this.data.formFields) {
  //           let formValue = new FormValue();
  //           formValue.fieldId = formField.id;
  //           this.formValues.push(formValue);
  //           this.genres = [];
            
  //           if (formField.type.name == 'enum' && formField.id == 'genre') {
  //             console.log(formField.type.values);
  //             for (let g in formField.type.values) {
  //               var genre = new Genre();
  //               genre.value = g;
  //               genre.checked = false;
  //               console.log(g);
  //               this.genres.push(genre);
  //             }
  //           }
  //         }
  //       });
  //     }
  //   );

    
  // }



  register() {
    console.log(this.formValues);
    console.log(this.genres);
    for (let formValue of this.formValues) {
      if (formValue.fieldId == "genre"){
        this.genres.forEach(element => {
          if (element.checked)
            formValue.fieldValue += element.value + "/";
        });
      } else if (formValue.fieldId == "betaReader") {
        formValue.fieldValue = this.betaReader.valueOf().toString();
      }
        
    }
    this.registerService.register(this.formValues, this.data.taskId, this.reg).subscribe(data => {
      
     
    },
      error => {
        console.log(error);
        this._snackBar.open("Something went wrong. :/.", 'x', {
          duration: 2000,
        });
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

  login() {
    console.log("login");
    this.userService.login(this.user).subscribe(
      (res) => {
        if (!this.userService.isTockenValid()) {
          this.router.navigateByUrl("");
        } else {
          this.router.navigateByUrl("/home");
        }
      }
    );
  }

  isAdmin(): any {
    if (this.userService.roles != null) {
      for (let role of this.userService.getRoles()) {
        if (role.name == "ROLE_ADMIN")
          return true;
      }
      return false;
    }
  }

  radioChange($event: MatRadioChange) {
    console.log($event.source.name, $event.value);
    this.visible = true;

    if ($event.value === "1") {
      this.registerService.startRegWriter().subscribe(
        res=> {
          console.log(res);
        }, error => {
          var processId =  error["error"]["text"];
          console.log(error["error"]["text"]);
          console.log(localStorage.getItem('processId'));
          localStorage.setItem('processId', processId);
          console.log(localStorage.getItem('processId'));
          this.processId =  processId;
        }
      );
    } else {
      console.log("reader");
      this.registerService.startReaderReg().subscribe(
        res=> {
          console.log(res);
        }, error => {
          var processId =  error["error"]["text"];
          localStorage.setItem('processId', processId);
          this.processId = processId;
        }
      );
    }
  }

  getReaderFields() {
    this.initData();
    this.reg = false;
    this.registerService.startReaderReg().subscribe(
      res => {
        console.log(res);
        var processId = res;
        
        console.log(processId);
        this.registerService.getReaderFormFields(processId).subscribe(
          res => {
            console.log(res);
          }
        );
      }, error => {
        if (error["statusText"] == "OK") {
          this.registerService.getReaderFormFields(error["error"]["text"]).subscribe(
            res => {
              var processId = res["processInstanceId"];
        
              console.log(processId);
              localStorage.setItem('processId', processId);
              console.log(res);
              this.data = res;
              for (let formField of this.data.formFields) {
                let formValue = new FormValue();
                formValue.fieldId = formField.id;
                this.formValues.push(formValue);
                this.genres = [];
                if (formField.type.name == 'enum' && formField.id == 'genre') {
                  console.log(formField.type.values);
                  for (let g in formField.type.values) {
                    var genre = new Genre();
                    genre.value = g;
                    genre.checked = false;
                    console.log(g);
                    this.genres.push(genre);
                  }
                }
              }
            }
          );
        }
      }
    );
  }

}
