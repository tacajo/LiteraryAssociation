import { Component, Input, OnInit } from '@angular/core';
import * as fileSaver from 'file-saver';
import { EditorService } from '../service/editor.service';
import { FileService } from '../service/file.service';
import { FormService } from '../service/form.service';

@Component({
  selector: 'app-download-books',
  templateUrl: './download-books.component.html',
  styleUrls: ['./download-books.component.css']
})
export class DownloadBooksComponent implements OnInit {

  constructor(private editorService : EditorService, private formService: FormService, private fileService: FileService) { }

  @Input() processId: string = '';
  data : any;
  showForm : boolean = false;
  download : any = 0;

  ngOnInit(): void {
    this.getBooks();
  }

  public getBooks(){
    this.editorService.getBooksPlagiarismDownload().subscribe(data => {
      this.data = data;
      this.showForm = true;
      if(data == null){
        this.showForm = false;
      }else{
        this.showForm = true;
      }
    });
  }

  downloadFile(naslov : any){
    console.log(naslov);
      this.fileService.download(this.processId, naslov).subscribe((response: BlobPart) => {
        let blob:any = new Blob([response], { type: 'application/pdf' });
        const url = URL.createObjectURL(blob);
        console.log(blob);
        fileSaver.saveAs(blob, naslov +'.pdf');
        this.download += 1;
        if(this.download == 2){
          this.fileService.compliteDownload(this.processId).subscribe(data=>{
            this.getBooks();
          });
        }
      }, (error: any) => {
        alert("You don't have permission to download!");
      });
  }

}
