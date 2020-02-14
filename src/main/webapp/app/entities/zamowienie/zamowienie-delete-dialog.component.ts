import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZamowienie } from 'app/shared/model/zamowienie.model';
import { ZamowienieService } from './zamowienie.service';

@Component({
  templateUrl: './zamowienie-delete-dialog.component.html'
})
export class ZamowienieDeleteDialogComponent {
  zamowienie: IZamowienie;

  constructor(
    protected zamowienieService: ZamowienieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.zamowienieService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'zamowienieListModification',
        content: 'Deleted an zamowienie'
      });
      this.activeModal.dismiss(true);
    });
  }
}
