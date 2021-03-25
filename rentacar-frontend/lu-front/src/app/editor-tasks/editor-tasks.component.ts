import { Component, OnInit } from '@angular/core';
import { DeclineModalComponent } from '../decline-modal/decline-modal.component';
import { EditorService } from '../service/editor.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-editor-tasks',
  templateUrl: './editor-tasks.component.html',
  styleUrls: ['./editor-tasks.component.css']
})
export class EditorTasksComponent implements OnInit {

  constructor(private editorService : EditorService, private modalService: NgbModal) { }

  showForm : boolean = false;
  data : any;

  ngOnInit(): void {
    this.getBooks();
  }

  public getBooks(){
    this.editorService.getBooks('urednik_pregleda_knjigu').subscribe(data => {
      this.data = data;
      this.showForm = true;
      if(data == null){
        this.showForm = false;
      }else{
        this.showForm = true;
      }
      //console.log(data);
    });
  }

  accept(username : any, title : any){
    console.log(username);
    let processId = localStorage.getItem('publishingProcessId_' + username + '_' + title); 
    let process = processId?.split("_")[0];
    let purpose = 'tasks';
    this.editorService.accept(process, purpose).subscribe(data => {
      this.getBooks();
    });
  }

  decline(username : any, title : any){
    console.log(username);
    const modalRef = this.modalService.open(DeclineModalComponent);
    modalRef.componentInstance.username = username;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.emitService.subscribe((emmitedValue: any) => {
      
      if(emmitedValue === true){
        this.getBooks();
      }
  });
  }

}
