import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IZamowienie } from 'app/shared/model/zamowienie.model';

type EntityResponseType = HttpResponse<IZamowienie>;
type EntityArrayResponseType = HttpResponse<IZamowienie[]>;

@Injectable({ providedIn: 'root' })
export class ZamowienieService {
  public resourceUrl = SERVER_API_URL + 'api/zamowienies';
  public resourceUrlZamowienie = SERVER_API_URL + 'api/zamowienie';

  constructor(protected http: HttpClient) {}

  create(zamowienie: IZamowienie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zamowienie);
    return this.http
      .post<IZamowienie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  createZamowienieWithUser(zamowienie: IZamowienie): Observable<EntityResponseType> {
    return this.http.post<IZamowienie>(this.resourceUrlZamowienie, zamowienie, { observe: 'response' });
  }

  update(zamowienie: IZamowienie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zamowienie);
    return this.http
      .put<IZamowienie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IZamowienie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IZamowienie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(zamowienie: IZamowienie): IZamowienie {
    const copy: IZamowienie = Object.assign({}, zamowienie, {
      dataUtworzenia:
        zamowienie.dataUtworzenia != null && zamowienie.dataUtworzenia.isValid() ? zamowienie.dataUtworzenia.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataUtworzenia = res.body.dataUtworzenia != null ? moment(res.body.dataUtworzenia) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((zamowienie: IZamowienie) => {
        zamowienie.dataUtworzenia = zamowienie.dataUtworzenia != null ? moment(zamowienie.dataUtworzenia) : null;
      });
    }
    return res;
  }
}
