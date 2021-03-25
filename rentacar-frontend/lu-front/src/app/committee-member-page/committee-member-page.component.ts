import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormField } from '../model/FormField';
import { Opinion } from '../model/Opinion';
import { CommitteeMemberService } from '../service/committee-member.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-committee-member-page',
  templateUrl: './committee-member-page.component.html',
  styleUrls: ['./committee-member-page.component.css']
})
export class CommitteeMemberPageComponent implements OnInit {

  constructor(public userService: UserService,
    private committeeServise: CommitteeMemberService,
    private _snackBar: MatSnackBar) { }

  imageURL: string = "assets/images/library-icon.png";
  public formFields : any;
  public formFieldsList : FormField[] = [];
  public pisac : any;
  public misljenje : any;
  showForm: boolean = false;
  opinion: Opinion[] = [];

  ngOnInit(): void {
    this.userService.getCommitteeMemberForm().subscribe(data => {
      console.log(data);
      if(data == null) {
          this.showForm = false;
      } else {
        console.log(data);
        this.showForm = true;
        this.formFieldsList = data;
        this.formFields = this.formFieldsList[0];
        console.log(this.formFields);
        this.formFields = this.formFields.formFields;
        console.log(this.formFields);
        this.formFields.forEach( (field : any) =>{
          this.opinion.push(new Opinion());
          if( field.type.name=='enum' && field.id=='misljenje'){
            this.misljenje = Object.keys(field.type.values);
          }
        });
        console.log(this.misljenje);
      }
        
    });
  }

  addOpinion(processInstanceId: any, taskId: any, index: number) {
    console.log(processInstanceId);
    console.log(taskId);
    console.log(this.opinion);
    console.log(index);
    console.log(this.opinion[index]);
    this.committeeServise.postOpinion(processInstanceId, taskId, this.opinion[index]).subscribe(
      res=> {
        this._snackBar.open("You have successfully given your opinion.", 'x', {
          duration: 2000,
        });
        this.ngOnInit();
      }, error => {
        this._snackBar.open("Something went wrong. :/", 'x', {
          duration: 2000,
        });
      }
    );
  }

}
