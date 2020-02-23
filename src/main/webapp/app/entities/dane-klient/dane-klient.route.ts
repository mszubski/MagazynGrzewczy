import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DaneKlient } from 'app/shared/model/dane-klient.model';
import { DaneKlientService } from './dane-klient.service';
import { DaneKlientComponent } from './dane-klient.component';
import { DaneKlientDetailComponent } from './dane-klient-detail.component';
import { DaneKlientUpdateComponent } from './dane-klient-update.component';
import { IDaneKlient } from 'app/shared/model/dane-klient.model';

@Injectable({ providedIn: 'root' })
export class DaneKlientResolve implements Resolve<IDaneKlient> {
  constructor(private service: DaneKlientService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDaneKlient> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((daneKlient: HttpResponse<DaneKlient>) => daneKlient.body));
    }
    return of(new DaneKlient());
  }
}

export const daneKlientRoute: Routes = [
  {
    path: '',
    component: DaneKlientComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Dane Klientów'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DaneKlientDetailComponent,
    resolve: {
      daneKlient: DaneKlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dane Klientów'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DaneKlientUpdateComponent,
    resolve: {
      daneKlient: DaneKlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dane Klientów'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DaneKlientUpdateComponent,
    resolve: {
      daneKlient: DaneKlientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dane Klientów'
    },
    canActivate: [UserRouteAccessService]
  }
];
