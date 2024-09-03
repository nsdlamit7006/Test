import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICitymaster, NewCitymaster } from '../citymaster.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICitymaster for edit and NewCitymasterFormGroupInput for create.
 */
type CitymasterFormGroupInput = ICitymaster | PartialWithRequiredKeyOf<NewCitymaster>;

type CitymasterFormDefaults = Pick<NewCitymaster, 'id' | 'status'>;

type CitymasterFormGroupContent = {
  id: FormControl<ICitymaster['id'] | NewCitymaster['id']>;
  name: FormControl<ICitymaster['name']>;
  cityCode: FormControl<ICitymaster['cityCode']>;
  status: FormControl<ICitymaster['status']>;
};

export type CitymasterFormGroup = FormGroup<CitymasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CitymasterFormService {
  createCitymasterFormGroup(citymaster: CitymasterFormGroupInput = { id: null }): CitymasterFormGroup {
    const citymasterRawValue = {
      ...this.getFormDefaults(),
      ...citymaster,
    };
    return new FormGroup<CitymasterFormGroupContent>({
      id: new FormControl(
        { value: citymasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(citymasterRawValue.name),
      cityCode: new FormControl(citymasterRawValue.cityCode),
      status: new FormControl(citymasterRawValue.status),
    });
  }

  getCitymaster(form: CitymasterFormGroup): ICitymaster | NewCitymaster {
    return form.getRawValue() as ICitymaster | NewCitymaster;
  }

  resetForm(form: CitymasterFormGroup, citymaster: CitymasterFormGroupInput): void {
    const citymasterRawValue = { ...this.getFormDefaults(), ...citymaster };
    form.reset(
      {
        ...citymasterRawValue,
        id: { value: citymasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CitymasterFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
