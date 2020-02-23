import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DaneKlientService } from 'app/entities/dane-klient/dane-klient.service';
import { IDaneKlient, DaneKlient } from 'app/shared/model/dane-klient.model';

describe('Service Tests', () => {
  describe('DaneKlient Service', () => {
    let injector: TestBed;
    let service: DaneKlientService;
    let httpMock: HttpTestingController;
    let elemDefault: IDaneKlient;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DaneKlientService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DaneKlient(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a DaneKlient', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DaneKlient(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DaneKlient', () => {
        const returnedFromService = Object.assign(
          {
            imie: 'BBBBBB',
            nazwisko: 'BBBBBB',
            numerTelefonu: 1,
            email: 'BBBBBB',
            firma: 'BBBBBB',
            ulica: 'BBBBBB',
            miejscowosc: 'BBBBBB',
            kodPocztowy: 'BBBBBB',
            kraj: 'BBBBBB',
            nip: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of DaneKlient', () => {
        const returnedFromService = Object.assign(
          {
            imie: 'BBBBBB',
            nazwisko: 'BBBBBB',
            numerTelefonu: 1,
            email: 'BBBBBB',
            firma: 'BBBBBB',
            ulica: 'BBBBBB',
            miejscowosc: 'BBBBBB',
            kodPocztowy: 'BBBBBB',
            kraj: 'BBBBBB',
            nip: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DaneKlient', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
