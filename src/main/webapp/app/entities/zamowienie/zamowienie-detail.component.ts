import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZamowienie } from 'app/shared/model/zamowienie.model';

@Component({
  selector: 'jhi-zamowienie-detail',
  templateUrl: './zamowienie-detail.component.html'
})
export class ZamowienieDetailComponent implements OnInit {
  zamowienie: IZamowienie;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zamowienie }) => {
      this.zamowienie = zamowienie;
    });
  }

  previousState() {
    window.history.back();
  }
}
