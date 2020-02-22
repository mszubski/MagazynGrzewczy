import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { DaneKlientDetailComponent } from 'app/entities/dane-klient/dane-klient-detail.component';
import { DaneKlient } from 'app/shared/model/dane-klient.model';

describe('Component Tests', () => {
  describe('DaneKlient Management Detail Component', () => {
    let comp: DaneKlientDetailComponent;
    let fixture: ComponentFixture<DaneKlientDetailComponent>;
    const route = ({ data: of({ daneKlient: new DaneKlient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [DaneKlientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DaneKlientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DaneKlientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.daneKlient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
