import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDaneKlient } from 'app/shared/model/dane-klient.model';

@Component({
  selector: 'jhi-dane-klient-detail',
  templateUrl: './dane-klient-detail.component.html'
})
export class DaneKlientDetailComponent implements OnInit {
  daneKlient: IDaneKlient;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ daneKlient }) => {
      this.daneKlient = daneKlient;
    });
  }

  previousState() {
    window.history.back();
  }
}
