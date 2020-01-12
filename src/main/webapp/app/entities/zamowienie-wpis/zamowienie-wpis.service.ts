import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IZamowienieWpis, ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { IProdukt } from 'app/shared/model/produkt.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { StatusZamowieniaEnum } from 'app/shared/model/enumerations/status-zamowienia-enum.model';

type EntityResponseType = HttpResponse<IZamowienieWpis>;
type EntityArrayResponseType = HttpResponse<IZamowienieWpis[]>;

@Injectable({ providedIn: 'root' })
export class ZamowienieWpisService {
  public resourceUrl = SERVER_API_URL + 'api/zamowienie-wpis';

  constructor(protected http: HttpClient) {}

  create(zamowienieWpis: IZamowienieWpis): Observable<EntityResponseType> {
    return this.http.post<IZamowienieWpis>(this.resourceUrl, zamowienieWpis, { observe: 'response' });
  }

  update(zamowienieWpis: IZamowienieWpis): Observable<EntityResponseType> {
    return this.http.put<IZamowienieWpis>(this.resourceUrl, zamowienieWpis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IZamowienieWpis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZamowienieWpis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  createProduktZamowienieWpis(produkt: IProdukt, ilosc: number) {
    let zamowienieWpis = new ZamowienieWpis();
    zamowienieWpis.produkt = produkt;
    zamowienieWpis.cena = produkt.cena;
    zamowienieWpis.ilosc = ilosc;
    zamowienieWpis.status = StatusEnum.KOSZYK;
    zamowienieWpis.statusZamowienia = StatusZamowieniaEnum.UTWORZONE;
    //zamowienieWpis.user = ;
    this.create(zamowienieWpis).subscribe();
  }
}
