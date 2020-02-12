import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProdukt } from 'app/shared/model/produkt.model';

type EntityResponseType = HttpResponse<IProdukt>;
type EntityArrayResponseType = HttpResponse<IProdukt[]>;

@Injectable({ providedIn: 'root' })
export class ProduktService {
  public resourceUrl = SERVER_API_URL + 'api/produkts';
  public resourceUrlProduktsXlsx = SERVER_API_URL + 'api/produkts/xlsx/';
  public resourceUrlAllProduktByKategoriaAkcesoria = SERVER_API_URL + 'api/produkts/akcesoria';
  public resourceUrlAllProduktByKategoriaPiece = SERVER_API_URL + 'api/produkts/piece';
  public resourceUrlAllProduktByKategoriaGrzejniki = SERVER_API_URL + 'api/produkts/grzejniki';
  public resourceUrlAllProduktByKategoriaKominki = SERVER_API_URL + 'api/produkts/kominki';

  constructor(protected http: HttpClient) {}

  create(produkt: IProdukt): Observable<EntityResponseType> {
    return this.http.post<IProdukt>(this.resourceUrl, produkt, { observe: 'response' });
  }

  update(produkt: IProdukt): Observable<EntityResponseType> {
    return this.http.put<IProdukt>(this.resourceUrl, produkt, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProdukt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdukt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAllProduktForAkcesoria(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdukt[]>(this.resourceUrlAllProduktByKategoriaAkcesoria, {
      params: options,
      observe: 'response'
    });
  }

  queryAllProduktForPiece(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdukt[]>(this.resourceUrlAllProduktByKategoriaPiece, {
      params: options,
      observe: 'response'
    });
  }

  queryAllProduktForGrzejniki(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdukt[]>(this.resourceUrlAllProduktByKategoriaGrzejniki, {
      params: options,
      observe: 'response'
    });
  }

  queryAllProduktForKominki(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdukt[]>(this.resourceUrlAllProduktByKategoriaKominki, {
      params: options,
      observe: 'response'
    });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAllProduktXlsx(path: string): Observable<EntityArrayResponseType> {
    return this.http.get<IProdukt[]>(this.resourceUrlProduktsXlsx + path, { observe: 'response' });
  }
}
