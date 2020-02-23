import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDaneKlient, DaneKlient } from 'app/shared/model/dane-klient.model';
import { DaneKlientService } from './dane-klient.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-dane-klient-update',
  templateUrl: './dane-klient-update.component.html'
})
export class DaneKlientUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    imie: [null, [Validators.required]],
    nazwisko: [null, [Validators.required]],
    numerTelefonu: [null, [Validators.required]],
    email: [null, [Validators.required]],
    firma: [null, [Validators.required]],
    ulica: [null, [Validators.required]],
    miejscowosc: [null, [Validators.required]],
    kodPocztowy: [null, [Validators.required]],
    kraj: [null, [Validators.required]],
    nip: [null, [Validators.required]],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected daneKlientService: DaneKlientService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ daneKlient }) => {
      this.updateForm(daneKlient);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(daneKlient: IDaneKlient) {
    this.editForm.patchValue({
      id: daneKlient.id,
      imie: daneKlient.imie,
      nazwisko: daneKlient.nazwisko,
      numerTelefonu: daneKlient.numerTelefonu,
      email: daneKlient.email,
      firma: daneKlient.firma,
      ulica: daneKlient.ulica,
      miejscowosc: daneKlient.miejscowosc,
      kodPocztowy: daneKlient.kodPocztowy,
      kraj: daneKlient.kraj,
      nip: daneKlient.nip,
      user: daneKlient.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const daneKlient = this.createFromForm();
    if (daneKlient.id !== undefined) {
      this.subscribeToSaveResponse(this.daneKlientService.update(daneKlient));
    } else {
      this.subscribeToSaveResponse(this.daneKlientService.create(daneKlient));
    }
  }

  private createFromForm(): IDaneKlient {
    return {
      ...new DaneKlient(),
      id: this.editForm.get(['id']).value,
      imie: this.editForm.get(['imie']).value,
      nazwisko: this.editForm.get(['nazwisko']).value,
      numerTelefonu: this.editForm.get(['numerTelefonu']).value,
      email: this.editForm.get(['email']).value,
      firma: this.editForm.get(['firma']).value,
      ulica: this.editForm.get(['ulica']).value,
      miejscowosc: this.editForm.get(['miejscowosc']).value,
      kodPocztowy: this.editForm.get(['kodPocztowy']).value,
      kraj: this.editForm.get(['kraj']).value,
      nip: this.editForm.get(['nip']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDaneKlient>>) {
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
}
