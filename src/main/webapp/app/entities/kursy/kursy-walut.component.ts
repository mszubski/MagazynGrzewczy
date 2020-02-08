import { Component, OnInit } from '@angular/core';
import { IKursyWalut } from 'app/shared/model/kursy-walut.model';

@Component({
  selector: 'jhi-kursy',
  templateUrl: './kursy-walut.component.html'
})
export class KursyWalutComponent implements OnInit {
  kursy: IKursyWalut[];

  constructor() {}

  ngOnInit() {}
}
