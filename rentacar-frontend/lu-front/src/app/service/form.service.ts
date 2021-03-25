import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  constructor(private http: HttpClient) { }

  getForm(processId: any) {
    return this.http.get<any>('http://localhost:8080/form/' + processId);
  }

  submitForm(taskId: any, dto : any) {
    console.log(dto);
    return this.http.post('http://localhost:8080/form/' + taskId, dto);
  }
}
