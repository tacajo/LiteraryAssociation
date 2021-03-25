import { Component, Input, OnInit } from '@angular/core';
import { EditorService } from '../service/editor.service';
import { FileService } from '../service/file.service';
import { FormService } from '../service/form.service';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-originals',
  templateUrl: './originals.component.html',
  styleUrls: ['./originals.component.css']
})
export class OriginalsComponent implements OnInit {

  constructor(private editorService : EditorService, private formService: FormService, private fileService: FileService) { }

  @Input() processId: string = '';
  data : any;
  showForm : boolean = false;

  ngOnInit(): void {
    this.getBooks();
  }

  public getBooks(){
    this.editorService.getBooks('Urednik_downloaduje_i_cita_rad').subscribe(data => {
      this.data = data;
      this.showForm = true;
      if(data == null){
        this.showForm = false;
      }else{
        this.showForm = true;
      }
    });
  }

  downloadFile(username : any, title : any) {
    console.log(username);
    let processId = localStorage.getItem('publishingProcessId_' + username + '_' + title); 
    let process = processId?.split("_")[0];

    this.fileService.downloadFile(process).subscribe((response: BlobPart) => {
			let blob:any = new Blob([response], { type: 'application/pdf' });
      const url = URL.createObjectURL(blob);
      console.log(blob);
      fileSaver.saveAs(blob, title +'.pdf');
      this.getBooks();
		});
  }

}
