import { Component, OnInit } from '@angular/core';
import { KursyWalutService } from 'app/entities/kursy/kursy-walut.service';
import { IKursyWalut } from 'app/shared/model/kursy-walut.model';
import { IKursyWalutSymbole } from 'app/shared/model/kursy-walut-symbole.model';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-kursy',
  templateUrl: './kursy-walut.component.html'
})
export class KursyWalutComponent implements OnInit {
  kurs: any;
  symbole: any;

  constructor(protected kursyWalut: KursyWalutService) {}

  ngOnInit() {
    this.kursyWalut.getKursyWalut().subscribe((res: HttpResponse<IKursyWalut>) => this.onSuccessSingle(res.body));
    this.kursyWalut.getKursyWalutSymbole().subscribe((res: HttpResponse<IKursyWalutSymbole>) => this.onSuccessSingleSymbole(res.body));
  }
  protected onSuccessSingle(kurs: IKursyWalut) {
    this.kurs = kurs;
  }

  private onSuccessSingleSymbole(symbole: IKursyWalutSymbole) {
    this.symbole = symbole;
  }
}
