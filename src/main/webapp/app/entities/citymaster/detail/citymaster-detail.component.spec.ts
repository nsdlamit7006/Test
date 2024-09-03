import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CitymasterDetailComponent } from './citymaster-detail.component';

describe('Citymaster Management Detail Component', () => {
  let comp: CitymasterDetailComponent;
  let fixture: ComponentFixture<CitymasterDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CitymasterDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CitymasterDetailComponent,
              resolve: { citymaster: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CitymasterDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CitymasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load citymaster on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CitymasterDetailComponent);

      // THEN
      expect(instance.citymaster()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
