import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICitymaster } from '../citymaster.model';
import { CitymasterService } from '../service/citymaster.service';

@Component({
  standalone: true,
  templateUrl: './citymaster-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CitymasterDeleteDialogComponent {
  citymaster?: ICitymaster;

  protected citymasterService = inject(CitymasterService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.citymasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
