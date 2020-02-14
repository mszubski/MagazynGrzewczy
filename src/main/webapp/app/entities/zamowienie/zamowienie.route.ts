import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Zamowienie } from 'app/shared/model/zamowienie.model';
import { ZamowienieService } from './zamowienie.service';
import { ZamowienieComponent } from './zamowienie.component';
import { ZamowienieDetailComponent } from './zamowienie-detail.component';
import { ZamowienieUpdateComponent } from './zamowienie-update.component';
import { IZamowienie } from 'app/shared/model/zamowienie.model';

@Injectable({ providedIn: 'root' })
export class ZamowienieResolve implements Resolve<IZamowienie> {
  constructor(private service: ZamowienieService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IZamowienie> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((zamowienie: HttpResponse<Zamowienie>) => zamowienie.body));
    }
    return of(new Zamowienie());
  }
}

export const zamowienieRoute: Routes = [
  {
    path: '',
    component: ZamowienieComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Zamowienies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ZamowienieDetailComponent,
    resolve: {
      zamowienie: ZamowienieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Zamowienies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ZamowienieUpdateComponent,
    resolve: {
      zamowienie: ZamowienieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Zamowienies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ZamowienieUpdateComponent,
    resolve: {
      zamowienie: ZamowienieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Zamowienies'
    },
    canActivate: [UserRouteAccessService]
  }
];
