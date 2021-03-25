import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import * as fileSaver from 'file-saver';
import { FormField } from '../model/FormField';
import { FormValue } from '../model/FormValue';
import { Genre } from '../model/Genre';
import { Opinion } from '../model/Opinion';
import { FileService } from '../service/file.service';
import { FormService } from '../service/form.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit, OnChanges {

  constructor(private formService: FormService, private fileService: FileService) { }

  ngOnInit(): void {
    console.log(this.formValues);
  }

  data: FormField = new FormField();
  formValues: FormValue[] = [];
  genres: any;
  betaReader: boolean = false;
  acceptedBook: boolean = false;
  selectedFiles! : FileList;
  booleanField: boolean = false;
  public misljenje: any;
  public opinion1 : any;
  public booksForPlagiarism : any;
  opinion: Opinion[] = [];
  editors: any;
  enum: any;

  @Input() processId: string = '';

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.processId);
    this.getFields();
  }

  getFields() {
    this.formService.getForm(localStorage.getItem('processId')).subscribe(
      data => {
        if(data == null) {
          alert("You are not assignee.");
          return;
        }
        this.data = data;
        console.log(this.data);
        this.formValues = [];
        for (let formField of this.data.formFields) {
          let formValue = new FormValue();
          formValue.fieldId = formField.id;
          this.formValues.push(formValue);
          this.genres = [];

          if (formField.type.name == 'enum' && (formField.id == 'genre' ||
            formField.id == 'genre2' || formField.id == 'misljenje' ||
            formField.id == 'editors' || formField.id == 'betaReaders' || formField.id == 'opinion')) {
            this.enum = Object.keys(formField.type.values);
          }
        }
        console.log(this.data);
      }, error => {
        console.log("usao u error");
        console.log(error);
      }
    );
  }

  selectFiles(event : any) {
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles)
  }

  submit() {
    console.log("usao u submit");
    for (let formValue of this.formValues) {
      if (formValue.fieldId == "betaReader") {
        formValue.fieldValue = this.betaReader.valueOf().toString();
      } else if (formValue.fieldId == "prihvataSe" || formValue.fieldId == "izmene" || formValue.fieldId == "saljeSe" ||
      formValue.fieldId == "odobrenje" || formValue.fieldId == "opinionCm") {
        formValue.fieldValue = this.booleanField.valueOf().toString();
      } else if (formValue.fieldId == "editors") {
        console.log(formValue.fieldValues);
        if (formValue.fieldValues.length < 2) {
          alert("Please choose at least 2 editors.");
          return;
        }
      }

    }

    if(this.selectedFiles.length < 2) {
      alert('You have to upload at least 2 pdfs.');
      return;
    }

    console.log(this.formValues);
    this.formService.submitForm(this.data.taskId, this.formValues).subscribe(
      data => {
        this.initData();
        this.formValues = [];
        if (this.betaReader) {
          this.betaReader = false;
          this.getFields();
          console.log("pokusao da napravi listu drugih zanrova");
        }
      }, error => {
        this.initData();
        console.log("pokusao da napravi listu drugih zanrova ali je usao u eror");
      }
    );
  }


  initData() {
    for (let field of this.formValues) {
      field.fieldValue = "";
    }
  }

  downloadFile(naslov : any){
    console.log(naslov);
      this.fileService.download(this.processId, naslov).subscribe((response: BlobPart) => {
        let blob:any = new Blob([response], { type: 'application/pdf' });
        const url = URL.createObjectURL(blob);
        console.log(blob);
        fileSaver.saveAs(blob, naslov +'.pdf');
      }, (error: any) => {
        alert("You don't have permission to download!");
      });
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
