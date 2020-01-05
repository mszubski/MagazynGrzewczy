import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProdukt } from 'app/shared/model/produkt.model';
import { ProduktService } from './produkt.service';

@Component({
  templateUrl: './produkt-delete-dialog.component.html'
})
export class ProduktDeleteDialogComponent {
  produkt: IProdukt;

  constructor(protected produktService: ProduktService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.produktService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'produktListModification',
        content: 'Deleted an produkt'
      });
      this.activeModal.dismiss(true);
    });
  }
}
