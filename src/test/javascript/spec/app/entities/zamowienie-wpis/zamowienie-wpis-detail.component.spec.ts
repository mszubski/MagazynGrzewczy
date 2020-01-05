import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MagazynGrzewczyTestModule } from '../../../test.module';
import { ZamowienieWpisDetailComponent } from 'app/entities/zamowienie-wpis/zamowienie-wpis-detail.component';
import { ZamowienieWpis } from 'app/shared/model/zamowienie-wpis.model';

describe('Component Tests', () => {
  describe('ZamowienieWpis Management Detail Component', () => {
    let comp: ZamowienieWpisDetailComponent;
    let fixture: ComponentFixture<ZamowienieWpisDetailComponent>;
    const route = ({ data: of({ zamowienieWpis: new ZamowienieWpis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MagazynGrzewczyTestModule],
        declarations: [ZamowienieWpisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ZamowienieWpisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZamowienieWpisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.zamowienieWpis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
