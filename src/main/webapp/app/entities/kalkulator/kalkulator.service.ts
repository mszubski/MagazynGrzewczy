import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class KalkulatorService {
  constructor(private http: HttpClient) {}

  ngOnInit() {}
}
