import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IZamowienie } from 'app/shared/model/zamowienie.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ZamowienieService } from './zamowienie.service';
import { ZamowienieDeleteDialogComponent } from './zamowienie-delete-dialog.component';
import { ZamowienieWpisService } from 'app/entities/zamowienie-wpis/zamowienie-wpis.service';
import { IZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';

@Component({
  selector: 'jhi-zamowienie',
  templateUrl: './zamowienie.component.html'
})
export class ZamowienieComponent implements OnInit, OnDestroy {
  zamowienies: IZamowienie[];
  zamowienieWpisArr: any[];
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
  current = 0;

  constructor(
    protected zamowienieService: ZamowienieService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected zamowienieWpisService: ZamowienieWpisService
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
    this.zamowienieService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IZamowienie[]>) => this.paginateZamowienies(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/zamowienie'], {
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
      '/zamowienie',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInZamowienies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IZamowienie) {
    return item.id;
  }

  registerChangeInZamowienies() {
    this.eventSubscriber = this.eventManager.subscribe('zamowienieListModification', () => this.loadAll());
  }

  delete(zamowienie: IZamowienie) {
    const modalRef = this.modalService.open(ZamowienieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.zamowienie = zamowienie;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateZamowienies(data: IZamowienie[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.zamowienies = data;
    this.loadFirstZamowienieWpis();
  }

  getAllZamowienieWpisByZamowienieId(zamowienieId: number) {
    this.zamowienieWpisService
      .findByZamowienieId(zamowienieId)
      .subscribe((res: HttpResponse<IZamowienieWpis[]>) => this.onSuccessSingle(res.body));
  }

  protected onSuccessSingle(zamowienieWpisArr: IZamowienieWpis[]) {
    this.zamowienieWpisArr = zamowienieWpisArr;
  }

  private loadFirstZamowienieWpis() {
    if (this.zamowienies.length) {
      const idFirstZamowienie = this.zamowienies[0].id;
      this.getAllZamowienieWpisByZamowienieId(idFirstZamowienie);
    }
  }

  getFirstZamowienieIdFromZamowienies() {
    return Array.isArray(this.zamowienies) && this.zamowienies.length ? this.zamowienies[0].id : 0;
  }
}
