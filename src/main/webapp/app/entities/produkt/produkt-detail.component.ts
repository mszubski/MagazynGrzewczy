import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProdukt } from 'app/shared/model/produkt.model';

@Component({
  selector: 'jhi-produkt-detail',
  templateUrl: './produkt-detail.component.html'
})
export class ProduktDetailComponent implements OnInit {
  produkt: IProdukt;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produkt }) => {
      this.produkt = produkt;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
