import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { DaneKlientUpdateComponent } from 'app/entities/dane-klient/dane-klient-update.component';
import { DaneKlientService } from 'app/entities/dane-klient/dane-klient.service';
import { DaneKlient } from 'app/shared/model/dane-klient.model';

describe('Component Tests', () => {
  describe('DaneKlient Management Update Component', () => {
    let comp: DaneKlientUpdateComponent;
    let fixture: ComponentFixture<DaneKlientUpdateComponent>;
    let service: DaneKlientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [DaneKlientUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DaneKlientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DaneKlientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DaneKlientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DaneKlient(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DaneKlient();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
