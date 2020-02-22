import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdukt } from 'app/shared/model/produkt.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProduktService } from './produkt.service';
import { ProduktDeleteDialogComponent } from './produkt-delete-dialog.component';
import { ZamowienieWpisService } from 'app/entities/zamowienie-wpis/zamowienie-wpis.service';
import { FormControl, Validators } from '@angular/forms';
import { AccountService } from 'app/core/auth/account.service';
import { User } from 'app/core/user/user.model';
import { IZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { ExcelServicesService } from 'app/entities/produkt/services/excel-services.service';
import { StatusProdukt } from 'app/shared/model/enumerations/status-produkt.model';

type EntityResponseType = HttpResponse<IZamowienieWpis>;

@Component({
  selector: 'jhi-produkt',
  templateUrl: './produkt.component.html'
})
export class ProduktComponent implements OnInit, OnDestroy {
  produkts: IProdukt[];
  login: string;
  user: User;
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
  ilosc = new FormControl(1, Validators.required);
  path: string;
  niedostepny = StatusProdukt.NIEDOSTEPNY;

  constructor(
    protected produktService: ProduktService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected zamowienieWpisService: ZamowienieWpisService,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected excelService: ExcelServicesService,
    protected http: HttpClient
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
    this.produktService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProdukt[]>) => this.paginateProdukts(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/produkt'], {
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
      '/produkt',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProdukts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProdukt) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInProdukts() {
    this.eventSubscriber = this.eventManager.subscribe('produktListModification', () => this.loadAll());
  }

  delete(produkt: IProdukt) {
    const modalRef = this.modalService.open(ProduktDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.produkt = produkt;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProdukts(data: IProdukt[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.produkts = data;
  }

  pobierzObecnegoUzytkownika(): User {
    console.log('pobierzObecnegoUzytkownika');
    let user = new User();
    this.accountService.identity().subscribe(account => {
      user.login = account.login;
      user.email = account.email;
    });
    console.log(user);
    return user;
  }

  dodajProduktZamowienieWpis(produkt: IProdukt, ilosc: number) {
    this.zamowienieWpisService.createProduktZamowienieWpis(produkt, ilosc);
  }

  private onError(error) {
    this.jhiAlertService.error(error.error, error.message, null);
  }

  getAllProduktXlsx(path) {
    console.log(path);
    this.produktService.getAllProduktXlsx(path).subscribe();
  }

  getAllAkcesoria() {
    this.produktService
      .queryAllProduktForAkcesoria()
      .subscribe((res: HttpResponse<IProdukt[]>) => this.paginateProdukts(res.body, res.headers));
  }

  getAllPiece() {
    this.produktService
      .queryAllProduktForPiece()
      .subscribe((res: HttpResponse<IProdukt[]>) => this.paginateProdukts(res.body, res.headers));
  }

  getAllGrzejniki() {
    this.produktService
      .queryAllProduktForGrzejniki()
      .subscribe((res: HttpResponse<IProdukt[]>) => this.paginateProdukts(res.body, res.headers));
  }

  getAllKominki() {
    this.produktService
      .queryAllProduktForKominki()
      .subscribe((res: HttpResponse<IProdukt[]>) => this.paginateProdukts(res.body, res.headers));
  }

  getAllProdukts() {
    this.loadAll();
  }

  exportAsXLSX(): void {
    this.excelService.exportAsExcelFile(this.produkts, 'ProduktyMagazyn');
  }
}
