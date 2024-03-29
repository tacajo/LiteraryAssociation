import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from '../service/user.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(public auth: UserService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    if (this.auth.tokenIsPresent()) {
        request = request.clone({
        setHeaders: {
            Authorization: `Bearer ${this.auth.getToken()}`
        }
        });
    }
    return next.handle(request);
  }
}