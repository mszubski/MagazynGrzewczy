import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { ZamowienieDeleteDialogComponent } from 'app/entities/zamowienie/zamowienie-delete-dialog.component';
import { ZamowienieService } from 'app/entities/zamowienie/zamowienie.service';

describe('Component Tests', () => {
  describe('Zamowienie Management Delete Component', () => {
    let comp: ZamowienieDeleteDialogComponent;
    let fixture: ComponentFixture<ZamowienieDeleteDialogComponent>;
    let service: ZamowienieService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [ZamowienieDeleteDialogComponent]
      })
        .overrideTemplate(ZamowienieDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZamowienieDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZamowienieService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
