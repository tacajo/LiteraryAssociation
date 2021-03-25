import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EditorService {

  constructor(private http: HttpClient) { }

  getBooks(task : any){
    return this.http.get<any>('http://localhost:8080/editor/get-books/' + task);
  }

  getBooksPlagiarismDownload(){
    return this.http.get<any>('http://localhost:8080/editor/get-books-plagiarism-download');
  }

  getBooksChooseOriginals(){
    return this.http.get<any>('http://localhost:8080/editor/get-books-choose-originals');
  }

  accept(processid : any, purpose : any){
    return this.http.get('http://localhost:8080/editor/accept/' + processid + '/' + purpose);
  }

  decline(processid : any, message : any){
    return this.http.post('http://localhost:8080/editor/decline/' + processid, { explanation : message });
  }
}
