import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { ZamowienieWpisUpdateComponent } from 'app/entities/zamowienie-wpis/zamowienie-wpis-update.component';
import { ZamowienieWpisService } from 'app/entities/zamowienie-wpis/zamowienie-wpis.service';
import { ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';

describe('Component Tests', () => {
  describe('ZamowienieWpis Management Update Component', () => {
    let comp: ZamowienieWpisUpdateComponent;
    let fixture: ComponentFixture<ZamowienieWpisUpdateComponent>;
    let service: ZamowienieWpisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [ZamowienieWpisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ZamowienieWpisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZamowienieWpisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZamowienieWpisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ZamowienieWpis(123);
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
        const entity = new ZamowienieWpis();
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
