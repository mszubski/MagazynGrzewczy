import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class KontaktService {
  public resourceUrlKontaktMailService = SERVER_API_URL + 'api/wyslijEmailKontakt';

  constructor(private http: HttpClient) {}

  ngOnInit() {}

  sendKontaktMail(formData: FormData) {
    return this.http.post(this.resourceUrlKontaktMailService, formData).subscribe();
  }
}
