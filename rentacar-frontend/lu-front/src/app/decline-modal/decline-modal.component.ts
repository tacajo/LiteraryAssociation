import { Component, EventEmitter, Inject, Input, OnInit, Optional, Output } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EditorTasksComponent } from '../editor-tasks/editor-tasks.component';
import { EditorService } from '../service/editor.service';

@Component({
  selector: 'app-decline-modal',
  templateUrl: './decline-modal.component.html',
  styleUrls: ['./decline-modal.component.css']
})
export class DeclineModalComponent implements OnInit {

  @Input() username : any;
  @Input() title : any;
  message : any = "";
  @Output() emitService = new EventEmitter();

  constructor(public activeModal: NgbActiveModal, private editorService : EditorService) { }

  ngOnInit(): void {
    console.log(this.username);
  }

  decline(){
    console.log(this.message);
    let processId = localStorage.getItem('publishingProcessId_' + this.username + '_' + this.title);
    let process = processId?.split("_")[0];
    this.editorService.decline(process, this.message).subscribe(data => {

      this.emitService.next(true);
      this.activeModal.close('Close click');
    });
  }

}
