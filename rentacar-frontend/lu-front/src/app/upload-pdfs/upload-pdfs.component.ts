import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { WriterService } from '../service/writer.service';

@Component({
  selector: 'app-upload-pdfs',
  templateUrl: './upload-pdfs.component.html',
  styleUrls: ['./upload-pdfs.component.css']
})
export class UploadPdfsComponent implements OnInit {

  constructor(private writerService : WriterService, private userService : UserService) { }

  showForm : boolean = false;
  data : any;
  formData : FormData = new FormData;
  selectedFiles! : FileList;

  ngOnInit(): void {
    this.getBooks();
  }

  public getBooks(){
    this.writerService.getBooks().subscribe(data => {
      this.data = data;
      this.showForm = true;
      console.log(data);
      if(data == null){
        this.showForm = false;
      }else{
        this.showForm = true;
      }
    });
  }

  selectFiles(event : any) {
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles)
  }

  savePdfs(title : any){
    let username = this.userService.getUsername();
    let processId = localStorage.getItem('publishingProcessId_' + username + '_' + title); 

    for(let i = 0; i < this.selectedFiles.length; i++){
      if(!(this.selectedFiles[i].type.match("application/pdf"))){
        alert('You can upload only pdf files.');
        return;
      }
    }

    for(let i = 0; i < this.selectedFiles.length; i++){
      console.log(this.selectedFiles[i]);
      this.formData.append('pdfs', this.selectedFiles[i]);
    }

    this.writerService.uploadPdfs(this.formData, username, processId).subscribe(data => {
        this.getBooks();
      }
    );
  }

}
