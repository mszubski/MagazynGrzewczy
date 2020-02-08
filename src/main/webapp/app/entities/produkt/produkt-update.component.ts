import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
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
    stan: [],
    kategoria: [],
    zdjecie: [],
    zdjecieContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected produktService: ProduktService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

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
      stan: produkt.stan,
      kategoria: produkt.kategoria,
      zdjecie: produkt.zdjecie,
      zdjecieContentType: produkt.zdjecieContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      stan: this.editForm.get(['stan']).value,
      kategoria: this.editForm.get(['kategoria']).value,
      zdjecieContentType: this.editForm.get(['zdjecieContentType']).value,
      zdjecie: this.editForm.get(['zdjecie']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
