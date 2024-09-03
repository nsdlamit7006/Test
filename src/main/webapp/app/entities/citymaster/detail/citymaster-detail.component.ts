import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICitymaster } from '../citymaster.model';

@Component({
  standalone: true,
  selector: 'jhi-citymaster-detail',
  templateUrl: './citymaster-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CitymasterDetailComponent {
  citymaster = input<ICitymaster | null>(null);

  previousState(): void {
    window.history.back();
  }
}
