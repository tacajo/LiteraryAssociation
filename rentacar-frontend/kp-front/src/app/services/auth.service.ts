import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http : HttpClient) { }

  confirmAcc(params : any){
    return this.http.post<any>('https://localhost:8443/auth/auth/login', params);
  }
}
