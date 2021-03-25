import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/User';
import { Writer } from '../model/Writer';
import {map} from 'rxjs/operators';
import { UserTokenState } from '../model/UserTokenState';
import { FormField } from '../model/FormField';
import { Book } from '../model/Book';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  private access_token : any;
  public currentUser : any = null;
  roles : any  = null;
  username = "";
  private temp : any = null;

  confirmAccount(token : String, processId : any){
    return this.http.get<Writer>('http://localhost:8080/user/confirmAccount/' + token + '/' + processId);
  }

  savePdfs(formData : FormData, username : string, taskId : any){
    return this.http.post('http://localhost:8080/user/upload-pdfs/' + username + '/' + taskId, formData);
  }

  savePdfsWriter(formData : FormData){
    return this.http.post('http://localhost:8080/writer/upload-pdfs', formData);
  }

  getPdfForm(processId : any){
    return this.http.get('http://localhost:8080/user/getPdfs/' + processId);
  }

  getCommitteeMemberForm(){
    return this.http.get<FormField[]>('http://localhost:8080/committee-member/getCommitteeMemberForm');
  }

  login(user: User) {
    console.log("usao i ovde");
    return this.http.post('http://localhost:8080/user/login', user)
      .pipe(map((res) => {
        this.access_token = res;
        this.access_token = this.access_token["accessToken"];
        console.log("token", this.access_token);
        localStorage.setItem("token", this.access_token);
        this.initUser();
      }));
  }

  tokenIsPresent() {
    this.access_token = localStorage.getItem("token");
    return this.access_token != undefined && this.access_token != null && this.access_token != "null";
  }

  getToken() {
    return localStorage.getItem("token");
  }

  isTockenValid() {
    return localStorage.getItem("token") != "error";
  }

  removeToken() {
    localStorage.setItem("token", "null");
  }

  getUser() {
    return this.http.get<User>('http://localhost:8080/user/user');   
  }

  tokenIsNull() {
    return localStorage.getItem("token") == null || localStorage.getItem("token") == "null";
  }

  initUser() {
    if(this.isTockenValid() && this.tokenIsPresent()) {
      return this.http.get('http://localhost:8080/user/user').subscribe(
      res => {
        if (res !== null) {
          console.log(res);
          this.temp = res;
          this.currentUser = this.temp;
          this.roles = this.temp["roles"];
          this.username = this.currentUser.username;
          localStorage.setItem("username", this.username);
        }
      },
      error => {
        console.log(error);
      });
    } else 
        return null;
    
  }

  getRoles() {
    return this.roles;
  }

  getRoleName() {
    if(this.roles != null) {
      if(this.roles[0].name == "ROLE_ADMIN")
        return "admin";
      else if (this.roles[0].name == "ROLE_WRITER")
        return "writer";
      else if (this.roles[0].name == "ROLE_COMMITTEEMEMBER")
        return "committee_member";
      else if (this.roles[0].name == "ROLE_READER")
        return "reader";
      else if (this.roles[0].name == "ROLE_EDITOR")
        return "editor";
      else 
        return "user";
    } else
      return null;
  }

  getUsername() {
    return localStorage.getItem("username");
  }

  payMembershipPee() {
    return this.http.put('http://localhost:8080/writer', "s");
  }

  getBooks() {
    return this.http.get<Book[]>('http://localhost:8080/book');
  }

  addBookToCart(bookId: any) {
    return this.http.put('http://localhost:8080/book/' + bookId, bookId);
  }

  getCart() {
    return this.http.get<Book[]>('http://localhost:8080/book/user');
  }

  getTransaction(uuid: any) {

  }
  
}
