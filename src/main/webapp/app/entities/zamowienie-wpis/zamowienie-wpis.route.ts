import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { ZamowienieWpisService } from './zamowienie-wpis.service';
import { ZamowienieWpisComponent } from './zamowienie-wpis.component';
import { ZamowienieWpisDetailComponent } from './zamowienie-wpis-detail.component';
import { ZamowienieWpisUpdateComponent } from './zamowienie-wpis-update.component';
import { IZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';

@Injectable({ providedIn: 'root' })
export class ZamowienieWpisResolve implements Resolve<IZamowienieWpis> {
  constructor(private service: ZamowienieWpisService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IZamowienieWpis> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((zamowienieWpis: HttpResponse<ZamowienieWpis>) => zamowienieWpis.body));
    }
    return of(new ZamowienieWpis());
  }
}

export const zamowienieWpisRoute: Routes = [
  {
    path: '',
    component: ZamowienieWpisComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Koszyk'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ZamowienieWpisDetailComponent,
    resolve: {
      zamowienieWpis: ZamowienieWpisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Koszyk'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ZamowienieWpisUpdateComponent,
    resolve: {
      zamowienieWpis: ZamowienieWpisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Koszyk Dodaj Nowy'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ZamowienieWpisUpdateComponent,
    resolve: {
      zamowienieWpis: ZamowienieWpisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Koszyk Edycja'
    },
    canActivate: [UserRouteAccessService]
  }
];
