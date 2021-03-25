import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WriterService {

  constructor(private http: HttpClient) { }

  publishingABook(username : any){
    return this.http.get<any>('http://localhost:8080/writer/' + username);
  }

  publish(form : any, taskId : any){
    return this.http.post('http://localhost:8080/writer/publish-book/' + taskId, form);
  }

  getBooks(){
    return this.http.get<any>('http://localhost:8080/writer/get-books');
  }

  uploadPdfs(formData : FormData, username : any, processId : any){
    return this.http.post('http://localhost:8080/writer/upload-pdfs/' + username + '/' + processId, formData, {responseType:'text'});
  }

  pay() {
    var processId = localStorage.getItem('processId');
    return this.http.put('http://localhost:8080/writer/pay/' + processId, processId);
  }
}
