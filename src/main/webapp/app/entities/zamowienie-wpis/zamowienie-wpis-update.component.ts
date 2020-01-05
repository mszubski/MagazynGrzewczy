import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IZamowienieWpis, ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { ZamowienieWpisService } from './zamowienie-wpis.service';

@Component({
  selector: 'jhi-zamowienie-wpis-update',
  templateUrl: './zamowienie-wpis-update.component.html'
})
export class ZamowienieWpisUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ilosc: [],
    cena: [],
    status: [],
    statusZamowienia: []
  });

  constructor(protected zamowienieWpisService: ZamowienieWpisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ zamowienieWpis }) => {
      this.updateForm(zamowienieWpis);
    });
  }

  updateForm(zamowienieWpis: IZamowienieWpis) {
    this.editForm.patchValue({
      id: zamowienieWpis.id,
      ilosc: zamowienieWpis.ilosc,
      cena: zamowienieWpis.cena,
      status: zamowienieWpis.status,
      statusZamowienia: zamowienieWpis.statusZamowienia
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
      statusZamowienia: this.editForm.get(['statusZamowienia']).value
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
}
