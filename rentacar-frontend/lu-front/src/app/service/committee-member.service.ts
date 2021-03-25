import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Opinion } from '../model/Opinion';

@Injectable({
  providedIn: 'root'
})
export class CommitteeMemberService {

  constructor(private http: HttpClient) { }

  postOpinion(processInstanceId : any, taskId : any, opinion: Opinion) {
    return this.http.post('http://localhost:8080/committee-member/' + processInstanceId + "/" + taskId, opinion);
  }
}
