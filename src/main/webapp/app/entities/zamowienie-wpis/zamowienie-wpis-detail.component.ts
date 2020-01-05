import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';

@Component({
  selector: 'jhi-zamowienie-wpis-detail',
  templateUrl: './zamowienie-wpis-detail.component.html'
})
export class ZamowienieWpisDetailComponent implements OnInit {
  zamowienieWpis: IZamowienieWpis;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zamowienieWpis }) => {
      this.zamowienieWpis = zamowienieWpis;
    });
  }

  previousState() {
    window.history.back();
  }
}
