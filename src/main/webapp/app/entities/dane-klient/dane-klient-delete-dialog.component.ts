import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDaneKlient } from 'app/shared/model/dane-klient.model';
import { DaneKlientService } from './dane-klient.service';

@Component({
  templateUrl: './dane-klient-delete-dialog.component.html'
})
export class DaneKlientDeleteDialogComponent {
  daneKlient: IDaneKlient;

  constructor(
    protected daneKlientService: DaneKlientService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.daneKlientService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'daneKlientListModification',
        content: 'Deleted an daneKlient'
      });
      this.activeModal.dismiss(true);
    });
  }
}
