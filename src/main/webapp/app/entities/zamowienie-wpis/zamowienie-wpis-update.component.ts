import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IZamowienieWpis, ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { ZamowienieWpisService } from './zamowienie-wpis.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProdukt } from 'app/shared/model/produkt.model';
import { ProduktService } from 'app/entities/produkt/produkt.service';

@Component({
  selector: 'jhi-zamowienie-wpis-update',
  templateUrl: './zamowienie-wpis-update.component.html'
})
export class ZamowienieWpisUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  produkts: IProdukt[];

  editForm = this.fb.group({
    id: [],
    ilosc: [],
    cena: [],
    status: [],
    statusZamowienia: [],
    zamowienieId: [],
    user: [],
    produkt: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected zamowienieWpisService: ZamowienieWpisService,
    protected userService: UserService,
    protected produktService: ProduktService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ zamowienieWpis }) => {
      this.updateForm(zamowienieWpis);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produktService
      .query()
      .subscribe((res: HttpResponse<IProdukt[]>) => (this.produkts = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(zamowienieWpis: IZamowienieWpis) {
    this.editForm.patchValue({
      id: zamowienieWpis.id,
      ilosc: zamowienieWpis.ilosc,
      cena: zamowienieWpis.cena,
      status: zamowienieWpis.status,
      statusZamowienia: zamowienieWpis.statusZamowienia,
      zamowienieId: zamowienieWpis.zamowienieId,
      user: zamowienieWpis.user,
      produkt: zamowienieWpis.produkt
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const zamowienieWpis = this.createFromForm();
    if (zamowienieWpis.id !== undefined) {
      this.subscribeToSaveResponse(this.zamowienieWpisService.update(zamowienieWpis));
    } else {
      this.subscribeToSaveResponse(this.zamowienieWpisService.create(zamowienieWpis));
    }
  }

  private createFromForm(): IZamowienieWpis {
    return {
      ...new ZamowienieWpis(),
      id: this.editForm.get(['id']).value,
      ilosc: this.editForm.get(['ilosc']).value,
      cena: this.editForm.get(['cena']).value,
      status: this.editForm.get(['status']).value,
      statusZamowienia: this.editForm.get(['statusZamowienia']).value,
      zamowienieId: this.editForm.get(['zamowienieId']).value,
      user: this.editForm.get(['user']).value,
      produkt: this.editForm.get(['produkt']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZamowienieWpis>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackProduktById(index: number, item: IProdukt) {
    return item.id;
  }
}
