import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IZamowienie, Zamowienie } from 'app/shared/model/zamowienie.model';
import { ZamowienieService } from './zamowienie.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-zamowienie-update',
  templateUrl: './zamowienie-update.component.html'
})
export class ZamowienieUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];
  dataUtworzeniaDp: any;

  editForm = this.fb.group({
    id: [],
    cena: [],
    status: [],
    dataUtworzenia: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected zamowienieService: ZamowienieService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ zamowienie }) => {
      this.updateForm(zamowienie);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(zamowienie: IZamowienie) {
    this.editForm.patchValue({
      id: zamowienie.id,
      cena: zamowienie.cena,
      status: zamowienie.status,
      dataUtworzenia: zamowienie.dataUtworzenia,
      user: zamowienie.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const zamowienie = this.createFromForm();
    if (zamowienie.id !== undefined) {
      this.subscribeToSaveResponse(this.zamowienieService.update(zamowienie));
    } else {
      this.subscribeToSaveResponse(this.zamowienieService.create(zamowienie));
    }
  }

  private createFromForm(): IZamowienie {
    return {
      ...new Zamowienie(),
      id: this.editForm.get(['id']).value,
      cena: this.editForm.get(['cena']).value,
      status: this.editForm.get(['status']).value,
      dataUtworzenia: this.editForm.get(['dataUtworzenia']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZamowienie>>) {
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
