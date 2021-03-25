import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PlagiarismService {

  constructor(private http: HttpClient) { }

  getFields() {
    return this.http.get<any>('http://localhost:8080/plagiarism');
  }

  getEditors() {
    return this.http.get<any>('http://localhost:8080/plagiarism/editors');
  }

  confirmPlagiarism(form : any, taskId : any) {
    return this.http.post('http://localhost:8080/plagiarism/entry-data/' + taskId, form);
  }
}
