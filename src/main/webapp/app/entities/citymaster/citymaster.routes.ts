import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CitymasterComponent } from './list/citymaster.component';
import { CitymasterDetailComponent } from './detail/citymaster-detail.component';
import { CitymasterUpdateComponent } from './update/citymaster-update.component';
import CitymasterResolve from './route/citymaster-routing-resolve.service';

const citymasterRoute: Routes = [
  {
    path: '',
    component: CitymasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CitymasterDetailComponent,
    resolve: {
      citymaster: CitymasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CitymasterUpdateComponent,
    resolve: {
      citymaster: CitymasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CitymasterUpdateComponent,
    resolve: {
      citymaster: CitymasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default citymasterRoute;
