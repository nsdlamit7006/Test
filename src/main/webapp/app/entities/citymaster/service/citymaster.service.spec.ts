import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICitymaster } from '../citymaster.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../citymaster.test-samples';

import { CitymasterService } from './citymaster.service';

const requireRestSample: ICitymaster = {
  ...sampleWithRequiredData,
};

describe('Citymaster Service', () => {
  let service: CitymasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICitymaster | ICitymaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CitymasterService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Citymaster', () => {
      const citymaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(citymaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Citymaster', () => {
      const citymaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(citymaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Citymaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Citymaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Citymaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCitymasterToCollectionIfMissing', () => {
      it('should add a Citymaster to an empty array', () => {
        const citymaster: ICitymaster = sampleWithRequiredData;
        expectedResult = service.addCitymasterToCollectionIfMissing([], citymaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(citymaster);
      });

      it('should not add a Citymaster to an array that contains it', () => {
        const citymaster: ICitymaster = sampleWithRequiredData;
        const citymasterCollection: ICitymaster[] = [
          {
            ...citymaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCitymasterToCollectionIfMissing(citymasterCollection, citymaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Citymaster to an array that doesn't contain it", () => {
        const citymaster: ICitymaster = sampleWithRequiredData;
        const citymasterCollection: ICitymaster[] = [sampleWithPartialData];
        expectedResult = service.addCitymasterToCollectionIfMissing(citymasterCollection, citymaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(citymaster);
      });

      it('should add only unique Citymaster to an array', () => {
        const citymasterArray: ICitymaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const citymasterCollection: ICitymaster[] = [sampleWithRequiredData];
        expectedResult = service.addCitymasterToCollectionIfMissing(citymasterCollection, ...citymasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const citymaster: ICitymaster = sampleWithRequiredData;
        const citymaster2: ICitymaster = sampleWithPartialData;
        expectedResult = service.addCitymasterToCollectionIfMissing([], citymaster, citymaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(citymaster);
        expect(expectedResult).toContain(citymaster2);
      });

      it('should accept null and undefined values', () => {
        const citymaster: ICitymaster = sampleWithRequiredData;
        expectedResult = service.addCitymasterToCollectionIfMissing([], null, citymaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(citymaster);
      });

      it('should return initial array if no Citymaster is added', () => {
        const citymasterCollection: ICitymaster[] = [sampleWithRequiredData];
        expectedResult = service.addCitymasterToCollectionIfMissing(citymasterCollection, undefined, null);
        expect(expectedResult).toEqual(citymasterCollection);
      });
    });

    describe('compareCitymaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCitymaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCitymaster(entity1, entity2);
        const compareResult2 = service.compareCitymaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCitymaster(entity1, entity2);
        const compareResult2 = service.compareCitymaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCitymaster(entity1, entity2);
        const compareResult2 = service.compareCitymaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
