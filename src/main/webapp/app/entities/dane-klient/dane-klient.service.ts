import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDaneKlient } from 'app/shared/model/dane-klient.model';

type EntityResponseType = HttpResponse<IDaneKlient>;
type EntityArrayResponseType = HttpResponse<IDaneKlient[]>;

@Injectable({ providedIn: 'root' })
export class DaneKlientService {
  public resourceUrl = SERVER_API_URL + 'api/dane-klients';

  constructor(protected http: HttpClient) {}

  create(daneKlient: IDaneKlient): Observable<EntityResponseType> {
    return this.http.post<IDaneKlient>(this.resourceUrl, daneKlient, { observe: 'response' });
  }

  update(daneKlient: IDaneKlient): Observable<EntityResponseType> {
    return this.http.put<IDaneKlient>(this.resourceUrl, daneKlient, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDaneKlient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDaneKlient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
