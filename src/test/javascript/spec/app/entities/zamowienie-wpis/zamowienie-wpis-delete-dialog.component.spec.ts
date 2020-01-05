import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { ZamowienieWpisDeleteDialogComponent } from 'app/entities/zamowienie-wpis/zamowienie-wpis-delete-dialog.component';
import { ZamowienieWpisService } from 'app/entities/zamowienie-wpis/zamowienie-wpis.service';

describe('Component Tests', () => {
  describe('ZamowienieWpis Management Delete Component', () => {
    let comp: ZamowienieWpisDeleteDialogComponent;
    let fixture: ComponentFixture<ZamowienieWpisDeleteDialogComponent>;
    let service: ZamowienieWpisService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [ZamowienieWpisDeleteDialogComponent]
      })
        .overrideTemplate(ZamowienieWpisDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZamowienieWpisDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZamowienieWpisService);
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
