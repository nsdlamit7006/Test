import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../citymaster.test-samples';

import { CitymasterFormService } from './citymaster-form.service';

describe('Citymaster Form Service', () => {
  let service: CitymasterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitymasterFormService);
  });

  describe('Service methods', () => {
    describe('createCitymasterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCitymasterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            cityCode: expect.any(Object),
            status: expect.any(Object),
          }),
        );
      });

      it('passing ICitymaster should create a new form with FormGroup', () => {
        const formGroup = service.createCitymasterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            cityCode: expect.any(Object),
            status: expect.any(Object),
          }),
        );
      });
    });

    describe('getCitymaster', () => {
      it('should return NewCitymaster for default Citymaster initial value', () => {
        const formGroup = service.createCitymasterFormGroup(sampleWithNewData);

        const citymaster = service.getCitymaster(formGroup) as any;

        expect(citymaster).toMatchObject(sampleWithNewData);
      });

      it('should return NewCitymaster for empty Citymaster initial value', () => {
        const formGroup = service.createCitymasterFormGroup();

        const citymaster = service.getCitymaster(formGroup) as any;

        expect(citymaster).toMatchObject({});
      });

      it('should return ICitymaster', () => {
        const formGroup = service.createCitymasterFormGroup(sampleWithRequiredData);

        const citymaster = service.getCitymaster(formGroup) as any;

        expect(citymaster).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICitymaster should not enable id FormControl', () => {
        const formGroup = service.createCitymasterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCitymaster should disable id FormControl', () => {
        const formGroup = service.createCitymasterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
