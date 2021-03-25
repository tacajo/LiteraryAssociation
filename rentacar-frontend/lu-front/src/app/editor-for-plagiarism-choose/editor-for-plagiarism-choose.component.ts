import { Component, OnInit } from '@angular/core';
import { Writer } from '../model/Writer';
import { PlagiarismService } from '../service/plagiarism.service';

@Component({
  selector: 'app-editor-for-plagiarism-choose',
  templateUrl: './editor-for-plagiarism-choose.component.html',
  styleUrls: ['./editor-for-plagiarism-choose.component.css']
})
export class EditorForPlagiarismChooseComponent implements OnInit {

  constructor(private service: PlagiarismService) { }
  editors : Writer[]=[];
  selectedItems = [];
  dropdownSettings = {};
  editorUsernames: String[] = [];
  processId: any;
  ngOnInit(): void {
    this.getEditors();
    console.log(localStorage.getItem('processId'));
    this.processId = localStorage.getItem('processId');
    console.log(this.processId);
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'username',
      textField: 'username',
      enableCheckAll: true,
     
      allowSearchFilter: true,
      limitSelection: -1,
      clearSearchFilter: true,
      maxHeight: 197,
      itemsShowLimit: 3,
      closeDropDownOnSelection: false,
      showSelectedItemsAtTop: false,
      defaultOpen: false
    };
  }

  getEditors(){
    this.service.getEditors().subscribe(data => {
      console.log(data);
     this.editors=data;

     for(let i =0;i<this.editors.length;i++){
       this.editorUsernames.push(this.editors[i].username);
     }
    },
    error => {
      console.log(error);
      alert('All marked fileds are required.');
    });
  }

  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }

}
