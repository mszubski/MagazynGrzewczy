import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IKursyWalut } from 'app/shared/model/kursy-walut.model';
import { IKursyWalutSymbole } from 'app/shared/model/kursy-walut-symbole.model';
import { SERVER_API_URL } from 'app/app.constants';

type EntityResponseType = HttpResponse<IKursyWalut>;
type EntityResponseTypeSymbole = HttpResponse<IKursyWalutSymbole>;

@Injectable({ providedIn: 'root' })
export class KursyWalutService {
  public resourceUrlKursyWalut = SERVER_API_URL + 'api/kursy';
  public resourceUrlKursyWalutSymbole = SERVER_API_URL + 'api/kursy/symbole';

  constructor(private http: HttpClient) {}

  getKursyWalut(): Observable<EntityResponseType> {
    return this.http.get<IKursyWalut>(this.resourceUrlKursyWalut, { observe: 'response' });
  }

  getKursyWalutSymbole(): Observable<EntityResponseTypeSymbole> {
    return this.http.get<IKursyWalutSymbole>(this.resourceUrlKursyWalutSymbole, { observe: 'response' });
  }
}
