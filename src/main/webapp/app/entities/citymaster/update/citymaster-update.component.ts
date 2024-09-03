import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICitymaster } from '../citymaster.model';
import { CitymasterService } from '../service/citymaster.service';
import { CitymasterFormService, CitymasterFormGroup } from './citymaster-form.service';

@Component({
  standalone: true,
  selector: 'jhi-citymaster-update',
  templateUrl: './citymaster-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CitymasterUpdateComponent implements OnInit {
  isSaving = false;
  citymaster: ICitymaster | null = null;

  protected citymasterService = inject(CitymasterService);
  protected citymasterFormService = inject(CitymasterFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CitymasterFormGroup = this.citymasterFormService.createCitymasterFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ citymaster }) => {
      this.citymaster = citymaster;
      if (citymaster) {
        this.updateForm(citymaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const citymaster = this.citymasterFormService.getCitymaster(this.editForm);
    if (citymaster.id !== null) {
      this.subscribeToSaveResponse(this.citymasterService.update(citymaster));
    } else {
      this.subscribeToSaveResponse(this.citymasterService.create(citymaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICitymaster>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(citymaster: ICitymaster): void {
    this.citymaster = citymaster;
    this.citymasterFormService.resetForm(this.editForm, citymaster);
  }
}
