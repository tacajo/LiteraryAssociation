import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormField } from '../model/FormField';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  startRegWriter() {
    return this.http.get('http://localhost:8080/user');
  }

  getFields(processId: any) {
    return this.http.get<any>('http://localhost:8080/user/' + processId);
  }

  register(form : any, taskId : any, reg:boolean) {
    if (reg) {
      return this.http.post('http://localhost:8080/user/entry-data/' + taskId, form);
    } else {
      return this.http.post('http://localhost:8080/reader/entry-data/' + taskId, form);
    }
  }

  getTaskId() {
    return this.http.get('http://localhost:8080/user/taskid');
  }

  startReaderReg() {
    return this.http.get('http://localhost:8080/reader');
  }

  getReaderFormFields(processId : any) {
    return this.http.get<any>('http://localhost:8080/reader/' + processId);
  }
  
}
