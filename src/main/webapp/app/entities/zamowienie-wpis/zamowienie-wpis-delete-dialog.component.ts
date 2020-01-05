import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';
import { ZamowienieWpisService } from './zamowienie-wpis.service';

@Component({
  templateUrl: './zamowienie-wpis-delete-dialog.component.html'
})
export class ZamowienieWpisDeleteDialogComponent {
  zamowienieWpis: IZamowienieWpis;

  constructor(
    protected zamowienieWpisService: ZamowienieWpisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.zamowienieWpisService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'zamowienieWpisListModification',
        content: 'Deleted an zamowienieWpis'
      });
      this.activeModal.dismiss(true);
    });
  }
}
