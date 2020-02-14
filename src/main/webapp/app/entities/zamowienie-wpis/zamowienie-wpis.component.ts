import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { of, Subscription } from 'rxjs';
import { JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IZamowienieWpis, ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ZamowienieWpisService } from './zamowienie-wpis.service';
import { ZamowienieWpisDeleteDialogComponent } from './zamowienie-wpis-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { ZamowienieService } from 'app/entities/zamowienie/zamowienie.service';
import { Zamowienie } from 'app/shared/model/zamowienie.model';
import { StatusZamowienieEnum } from 'app/shared/model/enumerations/status-zamowienie-enum.model';

@Component({
  selector: 'jhi-zamowienie-wpis',
  templateUrl: './zamowienie-wpis.component.html'
})
export class ZamowienieWpisComponent implements OnInit, OnDestroy {
  zamowienieWpis: IZamowienieWpis[];
  // zamowienie: Zamowienie;
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected zamowienieWpisService: ZamowienieWpisService,
    protected zamowienieService: ZamowienieService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected accountService: AccountService,
    protected dataUtils: JhiDataUtils
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.zamowienieWpisService
      .queryForUserKoszyk({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IZamowienieWpis[]>) => this.paginateZamowienieWpis(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/zamowienie-wpis'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/zamowienie-wpis',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInZamowienieWpis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IZamowienieWpis) {
    return item.id;
  }

  registerChangeInZamowienieWpis() {
    this.eventSubscriber = this.eventManager.subscribe('zamowienieWpisListModification', () => this.loadAll());
  }

  delete(zamowienieWpis: IZamowienieWpis) {
    const modalRef = this.modalService.open(ZamowienieWpisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.zamowienieWpis = zamowienieWpis;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateZamowienieWpis(data: IZamowienieWpis[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.zamowienieWpis = data;
  }

  getUserByKoszyk() {
    this.zamowienieWpisService
      .queryForUserKoszyk()
      .subscribe((res: HttpResponse<IZamowienieWpis[]>) => this.paginateZamowienieWpis(res.body, res.headers));
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  addZamowienie() {
    let zamowienie = new Zamowienie();
    zamowienie.cena = 0;
    zamowienie.dataUtworzenia = null;
    zamowienie.status = StatusZamowienieEnum.UTWORZONE;
    this.zamowienieService.createZamowienieWithUser(zamowienie).subscribe();
  }
}
