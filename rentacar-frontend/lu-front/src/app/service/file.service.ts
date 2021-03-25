import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {}

  downloadFile(process : any): any {
		return this.http.get('http://localhost:8080/download/' + process, {responseType: 'blob'});
  }

  download(process : any, title : any): any {
		return this.http.get('http://localhost:8080/download/' + process + '/' + title, {responseType: 'blob'});
  }

  compliteDownload(processId: any) {
    return this.http.get('http://localhost:8080/download/compliteDownload/' + processId);
  }
}
