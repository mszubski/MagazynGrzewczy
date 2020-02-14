import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ZamowienieService } from 'app/entities/zamowienie/zamowienie.service';
import { IZamowienie, Zamowienie } from 'app/shared/model/zamowienie.model';
import { StatusZamowienieEnum } from 'app/shared/model/enumerations/status-zamowienie-enum.model';

describe('Service Tests', () => {
  describe('Zamowienie Service', () => {
    let injector: TestBed;
    let service: ZamowienieService;
    let httpMock: HttpTestingController;
    let elemDefault: IZamowienie;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ZamowienieService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Zamowienie(0, 0, StatusZamowienieEnum.UTWORZONE, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataUtworzenia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Zamowienie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataUtworzenia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataUtworzenia: currentDate
          },
          returnedFromService
        );
        service
          .create(new Zamowienie(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Zamowienie', () => {
        const returnedFromService = Object.assign(
          {
            cena: 1,
            status: 'BBBBBB',
            dataUtworzenia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataUtworzenia: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Zamowienie', () => {
        const returnedFromService = Object.assign(
          {
            cena: 1,
            status: 'BBBBBB',
            dataUtworzenia: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataUtworzenia: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Zamowienie', () => {
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
