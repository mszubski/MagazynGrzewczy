import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IZamowienieWpis, ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { IProdukt } from 'app/shared/model/produkt.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { StatusZamowieniaEnum } from 'app/shared/model/enumerations/status-zamowienia-enum.model';
import { UserService } from 'app/core/user/user.service';

type EntityResponseType = HttpResponse<IZamowienieWpis>;
type EntityArrayResponseType = HttpResponse<IZamowienieWpis[]>;

@Injectable({ providedIn: 'root' })
export class ZamowienieWpisService {
  public resourceUrl = SERVER_API_URL + 'api/zamowienie-wpis';
  public resourceUrlZamowienieWpis = SERVER_API_URL + 'api/zamowienie-wpis-principal';
  public resourceUrlByUser = SERVER_API_URL + 'api/zamowienie-wpis/user-koszyk';
  public resourceUrlByZamowienieId = SERVER_API_URL + 'api/zamowieniewpis';

  constructor(protected http: HttpClient, protected userService: UserService) {}

  create(zamowienieWpis: IZamowienieWpis): Observable<EntityResponseType> {
    return this.http.post<IZamowienieWpis>(this.resourceUrl, zamowienieWpis, { observe: 'response' });
  }

  createZamowienieWpisWithUser(zamowienieWpis: IZamowienieWpis): Observable<EntityResponseType> {
    return this.http.post<IZamowienieWpis>(this.resourceUrlZamowienieWpis, zamowienieWpis, { observe: 'response' });
  }

  update(zamowienieWpis: IZamowienieWpis): Observable<EntityResponseType> {
    return this.http.put<IZamowienieWpis>(this.resourceUrl, zamowienieWpis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IZamowienieWpis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByZamowienieId(zamowienieId: number): Observable<EntityResponseType> {
    return this.http.get<IZamowienieWpis>(`${this.resourceUrlByZamowienieId}/${zamowienieId}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZamowienieWpis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryForUserKoszyk(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZamowienieWpis[]>(this.resourceUrlByUser, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  createProduktZamowienieWpis(produkt: IProdukt, ilosc: number) {
    let zamowienieWpis = new ZamowienieWpis();
    if (ilosc > 0) {
      zamowienieWpis.produkt = produkt;
      zamowienieWpis.cena = produkt.cena * ilosc;
      zamowienieWpis.ilosc = ilosc;
      zamowienieWpis.status = StatusEnum.KOSZYK;
      zamowienieWpis.statusZamowienia = StatusZamowieniaEnum.UTWORZONE;
      this.createZamowienieWpisWithUser(zamowienieWpis).subscribe();
    } else null;
  }
}
