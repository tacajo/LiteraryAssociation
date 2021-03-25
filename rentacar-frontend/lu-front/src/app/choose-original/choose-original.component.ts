import { Component, Input, OnInit } from '@angular/core';
import { EditorService } from '../service/editor.service';

@Component({
  selector: 'app-choose-original',
  templateUrl: './choose-original.component.html',
  styleUrls: ['./choose-original.component.css']
})
export class ChooseOriginalComponent implements OnInit {

  constructor(private editorService : EditorService) { }

  @Input() processId: string = '';
  data : any;
  showForm : boolean = false;

  ngOnInit(): void {
    this.getBooks();
  }

  public getBooks(){
    this.editorService.getBooks('Urednik_odlucuje_da_li_je_delo_originalno').subscribe(data => {
      this.data = data;
      this.showForm = true;
      if(data == null){
        this.showForm = false;
      }else{
        this.showForm = true;
      }
    });
  }

  accept(username : any, title : any){
    console.log(username);
    let processId = localStorage.getItem('publishingProcessId_' + username + '_' + title); 
    let process = processId?.split("_")[0];
    let purpose = 'chooseOriginal';
    this.editorService.accept(process, purpose).subscribe(data => {
      this.getBooks();
    });
  }

  decline(username : any, title : any){
    let processId = localStorage.getItem('publishingProcessId_' + username + '_' + title);
    let process = processId?.split("_")[0];
    this.editorService.decline(process, '').subscribe(data => {
      this.getBooks();
    });
  }


}
