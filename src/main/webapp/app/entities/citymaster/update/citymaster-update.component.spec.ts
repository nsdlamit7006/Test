import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CitymasterService } from '../service/citymaster.service';
import { ICitymaster } from '../citymaster.model';
import { CitymasterFormService } from './citymaster-form.service';

import { CitymasterUpdateComponent } from './citymaster-update.component';

describe('Citymaster Management Update Component', () => {
  let comp: CitymasterUpdateComponent;
  let fixture: ComponentFixture<CitymasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let citymasterFormService: CitymasterFormService;
  let citymasterService: CitymasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CitymasterUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CitymasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CitymasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    citymasterFormService = TestBed.inject(CitymasterFormService);
    citymasterService = TestBed.inject(CitymasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const citymaster: ICitymaster = { id: 456 };

      activatedRoute.data = of({ citymaster });
      comp.ngOnInit();

      expect(comp.citymaster).toEqual(citymaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICitymaster>>();
      const citymaster = { id: 123 };
      jest.spyOn(citymasterFormService, 'getCitymaster').mockReturnValue(citymaster);
      jest.spyOn(citymasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ citymaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: citymaster }));
      saveSubject.complete();

      // THEN
      expect(citymasterFormService.getCitymaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(citymasterService.update).toHaveBeenCalledWith(expect.objectContaining(citymaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICitymaster>>();
      const citymaster = { id: 123 };
      jest.spyOn(citymasterFormService, 'getCitymaster').mockReturnValue({ id: null });
      jest.spyOn(citymasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ citymaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: citymaster }));
      saveSubject.complete();

      // THEN
      expect(citymasterFormService.getCitymaster).toHaveBeenCalled();
      expect(citymasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICitymaster>>();
      const citymaster = { id: 123 };
      jest.spyOn(citymasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ citymaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(citymasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
