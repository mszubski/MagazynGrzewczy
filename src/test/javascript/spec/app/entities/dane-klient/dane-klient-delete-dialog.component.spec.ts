import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { DaneKlientDeleteDialogComponent } from 'app/entities/dane-klient/dane-klient-delete-dialog.component';
import { DaneKlientService } from 'app/entities/dane-klient/dane-klient.service';

describe('Component Tests', () => {
  describe('DaneKlient Management Delete Component', () => {
    let comp: DaneKlientDeleteDialogComponent;
    let fixture: ComponentFixture<DaneKlientDeleteDialogComponent>;
    let service: DaneKlientService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [DaneKlientDeleteDialogComponent]
      })
        .overrideTemplate(DaneKlientDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DaneKlientDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DaneKlientService);
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
