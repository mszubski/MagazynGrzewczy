import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProdukt, Produkt } from 'app/shared/model/produkt.model';
import { ProduktService } from './produkt.service';

@Component({
  selector: 'jhi-produkt-update',
  templateUrl: './produkt-update.component.html'
})
export class ProduktUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nazwa: [],
    cena: [],
    opis: [],
    status: [],
    zdjecie: [],
    stan: []
  });

  constructor(protected produktService: ProduktService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ produkt }) => {
      this.updateForm(produkt);
    });
  }

  updateForm(produkt: IProdukt) {
    this.editForm.patchValue({
      id: produkt.id,
      nazwa: produkt.nazwa,
      cena: produkt.cena,
      opis: produkt.opis,
      status: produkt.status,
      zdjecie: produkt.zdjecie,
      stan: produkt.stan
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const produkt = this.createFromForm();
    if (produkt.id !== undefined) {
      this.subscribeToSaveResponse(this.produktService.update(produkt));
    } else {
      this.subscribeToSaveResponse(this.produktService.create(produkt));
    }
  }

  private createFromForm(): IProdukt {
    return {
      ...new Produkt(),
      id: this.editForm.get(['id']).value,
      nazwa: this.editForm.get(['nazwa']).value,
      cena: this.editForm.get(['cena']).value,
      opis: this.editForm.get(['opis']).value,
      status: this.editForm.get(['status']).value,
      zdjecie: this.editForm.get(['zdjecie']).value,
      stan: this.editForm.get(['stan']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdukt>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
