import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICitymaster } from '../citymaster.model';
import { CitymasterService } from '../service/citymaster.service';

const citymasterResolve = (route: ActivatedRouteSnapshot): Observable<null | ICitymaster> => {
  const id = route.params['id'];
  if (id) {
    return inject(CitymasterService)
      .find(id)
      .pipe(
        mergeMap((citymaster: HttpResponse<ICitymaster>) => {
          if (citymaster.body) {
            return of(citymaster.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default citymasterResolve;
